/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.rageshake.impl.reporter

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import tech.orbitweb.orbitx.appconfig.RageshakeConfig
import tech.orbitweb.orbitx.features.enterprise.api.BugReportUrl
import tech.orbitweb.orbitx.features.enterprise.api.EnterpriseService
import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId
import tech.orbitweb.orbitx.libraries.sessionstorage.api.SessionStore
import tech.orbitweb.orbitx.libraries.sessionstorage.api.sessionIdFlow

@ContributesBinding(AppScope::class)
class DefaultBugReporterUrlProvider(
    private val bugReportAppNameProvider: BugReportAppNameProvider,
    private val enterpriseService: EnterpriseService,
    private val sessionStore: SessionStore,
) : BugReporterUrlProvider {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun provide(): Flow<HttpUrl?> {
        if (bugReportAppNameProvider.provide().isEmpty()) return flowOf(null)
        return sessionStore.sessionIdFlow().flatMapLatest { sessionId ->
            enterpriseService.bugReportUrlFlow(sessionId?.let(::SessionId))
                .map { bugReportUrl ->
                    when (bugReportUrl) {
                        is BugReportUrl.Custom -> bugReportUrl.url
                        BugReportUrl.Disabled -> null
                        BugReportUrl.UseDefault -> RageshakeConfig.BUG_REPORT_URL.takeIf { it.isNotEmpty() }
                    }
                }
                .map { it?.toHttpUrl() }
        }
    }
}
