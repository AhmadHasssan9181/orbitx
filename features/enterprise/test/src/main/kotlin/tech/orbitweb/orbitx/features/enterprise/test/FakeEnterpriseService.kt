/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.enterprise.test

import androidx.compose.ui.graphics.Color
import tech.orbitweb.orbitx.compound.colors.SemanticColorsLightDark
import tech.orbitweb.orbitx.features.enterprise.api.BugReportUrl
import tech.orbitweb.orbitx.features.enterprise.api.EnterpriseService
import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError
import tech.orbitweb.orbitx.tests.testutils.simulateLongTask
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeEnterpriseService(
    override val isEnterpriseBuild: Boolean = false,
    private val isEnterpriseUserResult: (SessionId) -> Boolean = { lambdaError() },
    private val defaultHomeserverListResult: () -> List<String> = { emptyList() },
    private val isAllowedToConnectToHomeserverResult: (String) -> Boolean = { lambdaError() },
    initialSemanticColors: SemanticColorsLightDark = SemanticColorsLightDark.default,
    initialBrandColor: Color? = null,
    private val overrideBrandColorResult: (SessionId?, String?) -> Unit = { _, _ -> lambdaError() },
    private val firebasePushGatewayResult: () -> String? = { lambdaError() },
    private val unifiedPushDefaultPushGatewayResult: () -> String? = { lambdaError() },
    private val getNoisyNotificationChannelIdResult: (SessionId?) -> String? = { lambdaError() },
    private val tweakMasUrlResult: (String, String) -> String = { _, _ -> lambdaError() },
) : EnterpriseService {
    private val brandColorState = MutableStateFlow(initialBrandColor)
    private val semanticColorsState = MutableStateFlow(initialSemanticColors)

    override suspend fun isEnterpriseUser(sessionId: SessionId): Boolean = simulateLongTask {
        isEnterpriseUserResult(sessionId)
    }

    override suspend fun tweakMasUrl(url: String, homeserver: String): String = simulateLongTask {
        tweakMasUrlResult(url, homeserver)
    }

    override fun defaultHomeserverList(): List<String> {
        return defaultHomeserverListResult()
    }

    override suspend fun isAllowedToConnectToHomeserver(homeserverUrl: String): Boolean = simulateLongTask {
        isAllowedToConnectToHomeserverResult(homeserverUrl)
    }

    override suspend fun overrideBrandColor(sessionId: SessionId?, brandColor: String?) = simulateLongTask {
        overrideBrandColorResult(sessionId, brandColor)
    }

    override fun brandColorsFlow(sessionId: SessionId?): Flow<Color?> {
        return brandColorState.asStateFlow()
    }

    override fun semanticColorsFlow(sessionId: SessionId?): Flow<SemanticColorsLightDark> {
        return semanticColorsState.asStateFlow()
    }

    override fun firebasePushGateway(): String? {
        return firebasePushGatewayResult()
    }

    override fun unifiedPushDefaultPushGateway(): String? {
        return unifiedPushDefaultPushGatewayResult()
    }

    val bugReportUrlMutableFlow = MutableStateFlow<BugReportUrl>(BugReportUrl.UseDefault)
    override fun bugReportUrlFlow(sessionId: SessionId?): Flow<BugReportUrl> {
        return bugReportUrlMutableFlow.asStateFlow()
    }

    override fun getNoisyNotificationChannelId(sessionId: SessionId): String? {
        return getNoisyNotificationChannelIdResult(sessionId)
    }
}
