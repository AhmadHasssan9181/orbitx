/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.pushproviders.unifiedpush

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId
import tech.orbitweb.orbitx.libraries.pushproviders.api.Config
import tech.orbitweb.orbitx.libraries.pushstore.api.clientsecret.PushClientSecret

interface UnifiedPushSessionPushConfigProvider {
    suspend fun provide(sessionId: SessionId): Config?
}

@ContributesBinding(AppScope::class)
class DefaultUnifiedPushPushConfigProvider(
    private val pushClientSecret: PushClientSecret,
    private val unifiedPushStore: UnifiedPushStore,
) : UnifiedPushSessionPushConfigProvider {
    override suspend fun provide(sessionId: SessionId): Config? {
        val clientSecret = pushClientSecret.getSecretForUser(sessionId)
        val url = unifiedPushStore.getPushGateway(clientSecret) ?: return null
        val pushKey = unifiedPushStore.getEndpoint(clientSecret) ?: return null
        return Config(
            url = url,
            pushKey = pushKey,
        )
    }
}
