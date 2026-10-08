/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.wellknown.api

/**
 * Server-driven settings for a user-chosen recovery passphrase, advertised under the well-known
 * `custom_recovery_passphrase` key. Today the only rule is a minimum character count; additional
 * settings can be added here as the schema grows.
 */
data class CustomRecoveryPassphrase(
    val minCharacterCount: Int,
) {
    /** True when [input] meets every active rule. */
    fun isSatisfiedBy(input: String): Boolean = isSatisfiedBy(input.length)

    /** True when an input of [length] characters meets every active rule. */
    fun isSatisfiedBy(length: Int): Boolean = length >= minCharacterCount
}
