/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.initializer

import android.content.Context
import android.system.Os
import androidx.startup.Initializer
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import tech.orbitweb.orbitx.di.AppBindings
import tech.orbitweb.orbitx.features.rageshake.api.logs.createWriteToFilesConfiguration
import tech.orbitweb.orbitx.libraries.architecture.bindings
import tech.orbitweb.orbitx.libraries.featureflag.api.FeatureFlags
import tech.orbitweb.orbitx.libraries.matrix.api.tracing.TracingConfiguration
import timber.log.Timber

private const val ORBITX_TARGET = "orbitx"

class PlatformInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        val appBindings = context.bindings<AppBindings>()
        val tracingService = appBindings.tracingService()
        val platformService = appBindings.platformService()
        val bugReporter = appBindings.bugReporter()
        Timber.plant(tracingService.createTimberTree(ORBITX_TARGET))
        Timber.plant(object : Timber.Tree() {
            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                android.util.Log.println(priority, tag ?: "OrbitX", message + (t?.let { "\n" + android.util.Log.getStackTraceString(it) } ?: ""))
            }
        })
        val preferencesStore = appBindings.preferencesStore()
        val featureFlagService = appBindings.featureFlagService()
        val logLevel = runBlocking { preferencesStore.getTracingLogLevelFlow().first() }
        val tracingConfiguration = TracingConfiguration(
            writesToLogcat = runBlocking { featureFlagService.isFeatureEnabled(FeatureFlags.PrintLogsToLogcat) },
            writesToFilesConfiguration = bugReporter.createWriteToFilesConfiguration(),
            logLevel = logLevel,
            extraTargets = listOf(ORBITX_TARGET),
            traceLogPacks = runBlocking { preferencesStore.getTracingLogPacksFlow().first() },
            sdkSentryDsn = appBindings.sentrySdkDsn()?.value?.takeIf { it.isNotBlank() },
        )
        bugReporter.setCurrentTracingLogLevel(logLevel.name)
        platformService.init(tracingConfiguration)
        // Also set env variable for rust back trace
        Os.setenv("RUST_BACKTRACE", "1", true)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = mutableListOf()
}
