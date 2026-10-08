/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.pushstore.impl.clientsecret

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId
import tech.orbitweb.orbitx.libraries.pushstore.api.clientsecret.PushClientSecret
import tech.orbitweb.orbitx.libraries.pushstore.api.clientsecret.PushClientSecretFactory
import tech.orbitweb.orbitx.libraries.pushstore.api.clientsecret.PushClientSecretStore

@ContributesBinding(AppScope::class)
class DefaultPushClientSecret(
    private val pushClientSecretFactory: PushClientSecretFactory,
    private val pushClientSecretStore: PushClientSecretStore,
) : PushClientSecret {
    override suspend fun getSecretForUser(userId: SessionId): String {
        val existingSecret = pushClientSecretStore.getSecret(userId)
        if (existingSecret != null) {
            return existingSecret
        }
        val newSecret = pushClientSecretFactory.create()
        pushClientSecretStore.storeSecret(userId, newSecret)
        return newSecret
    }

    override suspend fun getUserIdFromSecret(clientSecret: String): SessionId? {
        return pushClientSecretStore.getUserIdFromSecret(clientSecret)
    }
}
