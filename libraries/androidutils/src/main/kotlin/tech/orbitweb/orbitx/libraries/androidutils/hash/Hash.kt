/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.androidutils.hash

import java.security.MessageDigest
import java.util.Locale

/**
 * Compute a Hash of a String, using SHA-512 algorithm.
 */
fun String.hash() = try {
    val digest = MessageDigest.getInstance("SHA-512")
    digest.update(toByteArray())
    digest.digest()
        .joinToString("") { String.format(Locale.ROOT, "%02X", it) }
        .lowercase(Locale.ROOT)
} catch (_: Exception) {
    // Should not happen, but just in case
    hashCode().toString()
}
