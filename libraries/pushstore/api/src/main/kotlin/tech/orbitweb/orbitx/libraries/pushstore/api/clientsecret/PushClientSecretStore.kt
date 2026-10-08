/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.pushstore.api.clientsecret

import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId

interface PushClientSecretStore {
    suspend fun storeSecret(userId: SessionId, clientSecret: String)
    suspend fun getSecret(userId: SessionId): String?
    suspend fun resetSecret(userId: SessionId)
    suspend fun getUserIdFromSecret(clientSecret: String): SessionId?
}
