/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.api.linknewdevice

interface CheckCodeSender {
    /**
     * Validates the given [code]. Returns true if the code is valid, false otherwise.
     * This method can be called multiple times to validate different codes.
     */
    suspend fun validate(code: UByte): Boolean

    /**
     * Sends the given [code].
     * This method can be called only once.
     */
    suspend fun send(code: UByte): Result<Unit>
}
