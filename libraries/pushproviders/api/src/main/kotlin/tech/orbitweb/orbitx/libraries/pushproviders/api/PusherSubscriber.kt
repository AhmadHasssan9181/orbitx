/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.pushproviders.api

import tech.orbitweb.orbitx.libraries.matrix.api.MatrixClient
import tech.orbitweb.orbitx.libraries.matrix.api.exception.ClientException

interface PusherSubscriber {
    /**
     * Register a pusher. Note that failure will be a [RegistrationFailure].
     */
    suspend fun registerPusher(matrixClient: MatrixClient, pushKey: String, gateway: String): Result<Unit>

    /**
     * Unregister a pusher.
     */
    suspend fun unregisterPusher(matrixClient: MatrixClient, pushKey: String, gateway: String): Result<Unit>
}

class RegistrationFailure(
    val clientException: ClientException,
    val isRegisteringAgain: Boolean
) : Exception(clientException)
