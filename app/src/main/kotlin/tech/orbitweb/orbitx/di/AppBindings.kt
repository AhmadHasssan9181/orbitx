/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.di

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import tech.orbitweb.orbitx.features.api.MigrationEntryPoint
import tech.orbitweb.orbitx.features.enterprise.api.EnterpriseService
import tech.orbitweb.orbitx.features.lockscreen.api.LockScreenEntryPoint
import tech.orbitweb.orbitx.features.lockscreen.api.LockScreenService
import tech.orbitweb.orbitx.features.rageshake.api.reporter.BugReporter
import tech.orbitweb.orbitx.libraries.core.meta.BuildMeta
import tech.orbitweb.orbitx.libraries.designsystem.utils.snackbar.SnackbarDispatcher
import tech.orbitweb.orbitx.libraries.di.identifiers.SentrySdkDsn
import tech.orbitweb.orbitx.libraries.featureflag.api.FeatureFlagService
import tech.orbitweb.orbitx.libraries.matrix.api.platform.InitPlatformService
import tech.orbitweb.orbitx.libraries.matrix.api.tracing.TracingService
import tech.orbitweb.orbitx.libraries.preferences.api.store.AppPreferencesStore
import tech.orbitweb.orbitx.services.analytics.api.AnalyticsService

@ContributesTo(AppScope::class)
interface AppBindings {
    fun snackbarDispatcher(): SnackbarDispatcher

    fun tracingService(): TracingService

    fun platformService(): InitPlatformService

    fun bugReporter(): BugReporter

    fun lockScreenService(): LockScreenService

    fun preferencesStore(): AppPreferencesStore

    fun migrationEntryPoint(): MigrationEntryPoint

    fun lockScreenEntryPoint(): LockScreenEntryPoint

    fun analyticsService(): AnalyticsService

    fun enterpriseService(): EnterpriseService

    fun featureFlagService(): FeatureFlagService

    fun buildMeta(): BuildMeta

    fun sentrySdkDsn(): SentrySdkDsn?
}
