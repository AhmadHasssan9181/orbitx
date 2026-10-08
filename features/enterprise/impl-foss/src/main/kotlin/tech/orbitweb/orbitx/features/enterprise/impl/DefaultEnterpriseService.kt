/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.enterprise.impl

import androidx.compose.ui.graphics.Color
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import tech.orbitweb.orbitx.compound.colors.SemanticColorsLightDark
import tech.orbitweb.orbitx.features.enterprise.api.BugReportUrl
import tech.orbitweb.orbitx.features.enterprise.api.EnterpriseService
import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@ContributesBinding(AppScope::class)
class DefaultEnterpriseService : EnterpriseService {
    override val isEnterpriseBuild = false

    override suspend fun isEnterpriseUser(sessionId: SessionId) = false
    override suspend fun tweakMasUrl(url: String, homeserver: String) = url
    override fun defaultHomeserverList(): List<String> = emptyList()
    override suspend fun isAllowedToConnectToHomeserver(homeserverUrl: String) = true

    override suspend fun overrideBrandColor(sessionId: SessionId?, brandColor: String?) = Unit

    override fun brandColorsFlow(sessionId: SessionId?): Flow<Color?> {
        return flowOf(null)
    }

    override fun semanticColorsFlow(sessionId: SessionId?): Flow<SemanticColorsLightDark> {
        return flowOf(SemanticColorsLightDark.default)
    }

    override fun firebasePushGateway(): String? = null
    override fun unifiedPushDefaultPushGateway(): String? = null

    override fun bugReportUrlFlow(sessionId: SessionId?): Flow<BugReportUrl> {
        return flowOf(BugReportUrl.UseDefault)
    }

    override fun getNoisyNotificationChannelId(sessionId: SessionId): String? = null
}
