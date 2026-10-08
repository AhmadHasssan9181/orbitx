/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.api.auth

/**
 * Checks the homeserver's compatibility with ORBIT Chat.
 */
interface HomeServerLoginCompatibilityChecker {
    /**
     * Performs the compatibility check given the homeserver's [url].
     * @return a `true` value if the homeserver is compatible, `false` if not, or a failure result if the check unexpectedly failed.
     */
    suspend fun check(url: String): Result<Boolean>
}
