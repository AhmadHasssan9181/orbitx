/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.encrypteddb.passphrase

import tech.orbitweb.orbitx.libraries.androidutils.crypto.ClientSecret

/**
 * An abstraction to implement secure providers for SQLCipher passphrases.
 */
interface DatabaseSecretProvider {
    /**
     * Returns a secret for SQLCipher.
     */
    fun getSecret(): ClientSecret

    /**
     * Resets the passphrase, for example by deleting the persisted secret. Returns `true` if the reset was successful, `false` otherwise.
     */
    fun reset(): Boolean
}
