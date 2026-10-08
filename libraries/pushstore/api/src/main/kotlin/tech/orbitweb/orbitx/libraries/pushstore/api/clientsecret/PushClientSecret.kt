/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.pushstore.api.clientsecret

import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId

interface PushClientSecret {
    /**
     * To call when registering a pusher. It will return the existing secret or create a new one.
     */
    suspend fun getSecretForUser(userId: SessionId): String

    /**
     * To call when receiving a push containing a client secret.
     * Return null if not found.
     */
    suspend fun getUserIdFromSecret(clientSecret: String): SessionId?
}
