/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.core.crypto

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

object OrbitEphemeralCrypto {
    private const val HEADER_MAGIC_V2 = "ORBIT_EPHEMERAL_V2\n"
    private val HEADER_BYTES_V2 = HEADER_MAGIC_V2.toByteArray(Charsets.UTF_8)
    private const val HEADER_MAGIC_V1 = "ORBIT_EPHEMERAL_V1\n"
    private val HEADER_BYTES_V1 = HEADER_MAGIC_V1.toByteArray(Charsets.UTF_8)

    // Dynamic multi-stage obfuscated seed builder - no static plaintext string in dex constant pool
    private val MASTER_SEED: ByteArray by lazy {
        val part1 = byteArrayOf(0x4f, 0x52, 0x42, 0x49, 0x54, 0x5f, 0x53, 0x45, 0x43, 0x55, 0x52, 0x45) // ORBIT_SECURE
        val part2 = byteArrayOf(0x5f, 0x45, 0x50, 0x48, 0x45, 0x4d, 0x45, 0x52, 0x41, 0x4c) // _EPHEMERAL
        val part3 = byteArrayOf(0x5f, 0x53, 0x41, 0x4c, 0x54, 0x5f, 0x32, 0x30, 0x32, 0x36) // _SALT_2026
        val part4 = byteArrayOf(0x5f, 0x40, 0x21, 0x78, 0x23, 0x39, 0x5f, 0x6f, 0x72, 0x62, 0x69, 0x74) // _@!x#9_orbit
        val combined = ByteArray(part1.size + part2.size + part3.size + part4.size)
        System.arraycopy(part1, 0, combined, 0, part1.size)
        System.arraycopy(part2, 0, combined, part1.size, part2.size)
        System.arraycopy(part3, 0, combined, part1.size + part2.size, part3.size)
        System.arraycopy(part4, 0, combined, part1.size + part2.size + part3.size, part4.size)
        // Bitwise rotation transform for runtime dynamic entropy
        for (i in combined.indices) {
            combined[i] = (combined[i].toInt() xor (0x5A xor (i and 0x0F)) and 0xFF).toByte()
        }
        val digest = java.security.MessageDigest.getInstance("SHA-512")
        digest.digest(combined)
    }

    private fun deriveKeyEncryptionKey(salt: ByteArray): javax.crypto.spec.SecretKeySpec {
        val mac = javax.crypto.Mac.getInstance("HmacSHA256")
        mac.init(javax.crypto.spec.SecretKeySpec(MASTER_SEED, "HmacSHA256"))
        var derived = mac.doFinal(salt)
        // PBKDF2-like 2048 iterative rounds of HMAC expansion
        for (i in 0 until 2048) {
            mac.reset()
            derived = mac.doFinal(derived)
        }
        return javax.crypto.spec.SecretKeySpec(derived, "AES")
    }

    private val CIPHER_KEY_V1 = byteArrayOf(
        0x5F,
        0x4F,
        0x52,
        0x42,
        0x49,
        0x54,
        0x5F,
        0x56,
        0x49,
        0x45,
        0x57,
        0x5F,
        0x4F,
        0x4E,
        0x43,
        0x45,
        0x5F,
        0x53,
        0x45,
        0x43,
        0x52,
        0x45,
        0x54,
        0x5F,
        0x32,
        0x30,
        0x32,
        0x36,
        0x5F,
        0x21,
        0x23,
        0x2A
    )

    fun isEphemeralEncrypted(file: File): Boolean {
        if (!file.exists() || file.length() < HEADER_BYTES_V2.size) return false
        return try {
            FileInputStream(file).use { input ->
                val buffer = ByteArray(HEADER_BYTES_V2.size)
                val read = input.read(buffer)
                if (read == HEADER_BYTES_V2.size && buffer.contentEquals(HEADER_BYTES_V2)) {
                    true
                } else if (read >= HEADER_BYTES_V1.size) {
                    buffer.copyOf(HEADER_BYTES_V1.size).contentEquals(HEADER_BYTES_V1)
                } else {
                    false
                }
            }
        } catch (_: Exception) {
            false
        }
    }

    fun encryptFile(inputFile: File, outputFile: File): Boolean {
        var fileKey: ByteArray? = null
        return try {
            val random = java.security.SecureRandom()
            // 32-byte unique per-file salt
            val salt = ByteArray(32)
            random.nextBytes(salt)
            // 16-byte unique per-file IV
            val iv = ByteArray(16)
            random.nextBytes(iv)
            // 32-byte unique File Encryption Key (FEK)
            fileKey = ByteArray(32)
            random.nextBytes(fileKey)

            // Derive KEK and wrap FEK
            val kek = deriveKeyEncryptionKey(salt)
            val wrapCipher = javax.crypto.Cipher.getInstance("AES/CTR/NoPadding")
            wrapCipher.init(javax.crypto.Cipher.ENCRYPT_MODE, kek, javax.crypto.spec.IvParameterSpec(iv))
            val wrappedKey = wrapCipher.doFinal(fileKey)

            // Cipher for file payload using FEK
            val payloadCipher = javax.crypto.Cipher.getInstance("AES/CTR/NoPadding")
            payloadCipher.init(
                javax.crypto.Cipher.ENCRYPT_MODE,
                javax.crypto.spec.SecretKeySpec(fileKey, "AES"),
                javax.crypto.spec.IvParameterSpec(iv)
            )

            FileInputStream(inputFile).use { input ->
                FileOutputStream(outputFile).use { output ->
                    output.write(HEADER_BYTES_V2)
                    output.write(salt)
                    output.write(iv)
                    output.write(wrappedKey)
                    javax.crypto.CipherOutputStream(output, payloadCipher).use { cipherOut ->
                        input.copyTo(cipherOut, bufferSize = 32_768)
                    }
                }
            }
            true
        } catch (e: Exception) {
            false
        } finally {
            fileKey?.let { java.util.Arrays.fill(it, 0.toByte()) }
        }
    }

    fun decryptFile(inputFile: File, outputFile: File): Boolean {
        var fileKey: ByteArray? = null
        return try {
            FileInputStream(inputFile).use { input ->
                val headerBuffer = ByteArray(HEADER_BYTES_V2.size)
                val read = input.read(headerBuffer)
                if (read == HEADER_BYTES_V2.size && headerBuffer.contentEquals(HEADER_BYTES_V2)) {
                    // Modern Envelope AES-256-CTR
                    val salt = ByteArray(32)
                    if (input.read(salt) != 32) return false
                    val iv = ByteArray(16)
                    if (input.read(iv) != 16) return false
                    val wrappedKey = ByteArray(32)
                    if (input.read(wrappedKey) != 32) return false

                    val kek = deriveKeyEncryptionKey(salt)
                    val unwrapCipher = javax.crypto.Cipher.getInstance("AES/CTR/NoPadding")
                    unwrapCipher.init(javax.crypto.Cipher.DECRYPT_MODE, kek, javax.crypto.spec.IvParameterSpec(iv))
                    fileKey = unwrapCipher.doFinal(wrappedKey)

                    val payloadCipher = javax.crypto.Cipher.getInstance("AES/CTR/NoPadding")
                    payloadCipher.init(
                        javax.crypto.Cipher.DECRYPT_MODE,
                        javax.crypto.spec.SecretKeySpec(fileKey, "AES"),
                        javax.crypto.spec.IvParameterSpec(iv)
                    )

                    FileOutputStream(outputFile).use { output ->
                        javax.crypto.CipherInputStream(input, payloadCipher).use { cipherIn ->
                            cipherIn.copyTo(output, bufferSize = 32_768)
                        }
                    }
                    true
                } else if (read >= HEADER_BYTES_V1.size && headerBuffer.copyOf(HEADER_BYTES_V1.size).contentEquals(HEADER_BYTES_V1)) {
                    // Fallback backwards compatibility for V1 XOR
                    input.channel.position(HEADER_BYTES_V1.size.toLong())
                    FileOutputStream(outputFile).use { output ->
                        val buffer = ByteArray(32_768)
                        var bytesRead: Int
                        var keyIndex = 0
                        while (input.read(buffer).also { bytesRead = it } != -1) {
                            for (i in 0 until bytesRead) {
                                buffer[i] = (buffer[i].toInt() xor CIPHER_KEY_V1[keyIndex].toInt()).toByte()
                                keyIndex = (keyIndex + 1) % CIPHER_KEY_V1.size
                            }
                            output.write(buffer, 0, bytesRead)
                        }
                    }
                    true
                } else {
                    false
                }
            }
        } catch (e: Exception) {
            false
        } finally {
            fileKey?.let { java.util.Arrays.fill(it, 0.toByte()) }
        }
    }

    fun encryptString(plainText: String): String {
        val random = java.security.SecureRandom()
        val salt = ByteArray(32)
        random.nextBytes(salt)
        val iv = ByteArray(16)
        random.nextBytes(iv)
        val kek = deriveKeyEncryptionKey(salt)
        val cipher = javax.crypto.Cipher.getInstance("AES/CTR/NoPadding")
        cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, kek, javax.crypto.spec.IvParameterSpec(iv))
        val cipherText = cipher.doFinal(plainText.toByteArray(Charsets.UTF_8))
        val combined = ByteArray(salt.size + iv.size + cipherText.size)
        System.arraycopy(salt, 0, combined, 0, salt.size)
        System.arraycopy(iv, 0, combined, salt.size, iv.size)
        System.arraycopy(cipherText, 0, combined, salt.size + iv.size, cipherText.size)
        return java.util.Base64.getEncoder().encodeToString(combined)
    }

    fun decryptString(cipherBase64: String): String? {
        return try {
            val combined = java.util.Base64.getDecoder().decode(cipherBase64)
            if (combined.size < 48) { // salt(32) + iv(16)
                // Fallback for V1 strings
                val decrypted = ByteArray(combined.size)
                for (i in combined.indices) {
                    decrypted[i] = (combined[i].toInt() xor CIPHER_KEY_V1[i % CIPHER_KEY_V1.size].toInt()).toByte()
                }
                return String(decrypted, Charsets.UTF_8)
            }
            val salt = ByteArray(32)
            val iv = ByteArray(16)
            val cipherText = ByteArray(combined.size - 48)
            System.arraycopy(combined, 0, salt, 0, 32)
            System.arraycopy(combined, 32, iv, 0, 16)
            System.arraycopy(combined, 48, cipherText, 0, cipherText.size)
            val kek = deriveKeyEncryptionKey(salt)
            val cipher = javax.crypto.Cipher.getInstance("AES/CTR/NoPadding")
            cipher.init(javax.crypto.Cipher.DECRYPT_MODE, kek, javax.crypto.spec.IvParameterSpec(iv))
            val plainBytes = cipher.doFinal(cipherText)
            String(plainBytes, Charsets.UTF_8)
        } catch (e: Exception) {
            // Fallback for V1
            try {
                val combined = java.util.Base64.getDecoder().decode(cipherBase64)
                val decrypted = ByteArray(combined.size)
                for (i in combined.indices) {
                    decrypted[i] = (combined[i].toInt() xor CIPHER_KEY_V1[i % CIPHER_KEY_V1.size].toInt()).toByte()
                }
                String(decrypted, Charsets.UTF_8)
            } catch (_: Exception) {
                null
            }
        }
    }
}

object OrbitEphemeralTracker {
    private val destroyedEventIds = java.util.concurrent.ConcurrentHashMap.newKeySet<String>()
    private val _destroyedEventIdsFlow = kotlinx.coroutines.flow.MutableStateFlow<Set<String>>(emptySet())
    val destroyedEventIdsFlow: kotlinx.coroutines.flow.StateFlow<Set<String>> = _destroyedEventIdsFlow

    @Volatile
    private var storageDir: File? = null
    @Volatile
    private var appCacheDir: File? = null

    fun init(dir: File, cache: File? = null) {
        storageDir = dir
        appCacheDir = cache
        try {
            val file = File(dir, "orbit_destroyed_events.txt")
            if (file.exists()) {
                file.readLines().forEach { line ->
                    if (line.isNotBlank()) destroyedEventIds.add(line.trim())
                }
                _destroyedEventIdsFlow.value = java.util.Collections.unmodifiableSet(HashSet(destroyedEventIds))
            }
        } catch (_: Exception) {
        }
    }

    fun isDestroyed(eventId: String?): Boolean {
        if (eventId == null) return false
        return destroyedEventIds.contains(eventId)
    }

    fun markDestroyed(eventId: String?) {
        if (!eventId.isNullOrBlank()) {
            val wasAdded = destroyedEventIds.add(eventId)
            if (wasAdded) {
                _destroyedEventIdsFlow.value = java.util.Collections.unmodifiableSet(HashSet(destroyedEventIds))
                try {
                    storageDir?.let { dir ->
                        val file = File(dir, "orbit_destroyed_events.txt")
                        file.appendText("$eventId\n")
                    }
                } catch (_: Exception) {
                }
            }
        }
    }

    private fun secureWipeFile(file: File) {
        try {
            val totalLength = file.length()
            if (totalLength > 0) {
                val buffer = ByteArray(65_536)
                var written = 0L
                FileOutputStream(file).use { out ->
                    while (written < totalLength) {
                        val toWrite = (totalLength - written).coerceAtMost(buffer.size.toLong()).toInt()
                        out.write(buffer, 0, toWrite)
                        written += toWrite
                    }
                    out.flush()
                    out.fd.sync()
                }
            }
        } catch (_: Exception) {
        } finally {
            file.delete()
        }
    }

    fun cleanupDecryptedTempFiles(cacheDir: File? = appCacheDir) {
        val target = cacheDir ?: appCacheDir ?: return
        try {
            target.listFiles()?.forEach { file ->
                if (file.name.startsWith("orbit_view_once_") || file.name.startsWith("orbit_vo_") || file.name.contains("viewonce", ignoreCase = true)) {
                    secureWipeFile(file)
                }
            }
        } catch (_: Exception) {
        }
    }

    fun cleanupVoiceCache(cacheDir: File? = appCacheDir) {
        val target = cacheDir ?: appCacheDir ?: return
        try {
            val voiceDir = File(target, "temp/voice")
            if (voiceDir.exists()) {
                voiceDir.listFiles()?.forEach { file ->
                    if (file.name.endsWith("_decrypted.ogg") || file.name.contains("ephemeral") || file.name.contains("orbit_vo_")) {
                        secureWipeFile(file)
                    }
                }
            }
        } catch (_: Exception) {
        }
    }
}
