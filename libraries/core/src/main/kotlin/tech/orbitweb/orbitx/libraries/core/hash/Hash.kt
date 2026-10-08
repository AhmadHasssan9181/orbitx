/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.core.hash

import java.security.MessageDigest
import java.util.Locale

/**
 * Compute a Hash of a String, using md5 algorithm.
 */
fun String.md5() = try {
    val digest = MessageDigest.getInstance("md5")
    val locale = Locale.ROOT
    digest.update(toByteArray())
    digest.digest()
        .joinToString("") { String.format(locale, "%02X", it) }
        .lowercase(locale)
} catch (_: Exception) {
    // Should not happen, but just in case
    hashCode().toString()
}
