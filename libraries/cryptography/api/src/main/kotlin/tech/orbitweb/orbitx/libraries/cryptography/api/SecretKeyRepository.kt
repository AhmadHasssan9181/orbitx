/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.cryptography.api

import kotlinx.coroutines.flow.Flow
import javax.crypto.SecretKey

/**
 * Simple interface to get, create and delete a secret key for a given alias.
 * Implementation should be able to store the generated key securely.
 */
interface SecretKeyRepository {
    fun hasKey(alias: String): Flow<Boolean>

    /**
     * Get or create a secret key for a given alias.
     * @param alias the alias to use
     * @param requiresUserAuthentication true if the key should be protected by user authentication
     */
    suspend fun getOrCreateKey(alias: String, requiresUserAuthentication: Boolean): SecretKey

    /**
     * Delete the secret key for a given alias.
     * @param alias the alias to use
     */
    suspend fun deleteKey(alias: String)
}
