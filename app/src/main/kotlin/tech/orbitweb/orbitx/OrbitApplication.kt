/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx

import android.app.Application
import androidx.compose.material3.ComposeMaterial3Flags.isAnchoredDraggableComponentsStrictOffsetCheckEnabled
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.startup.AppInitializer
import androidx.work.Configuration
import dev.zacsweers.metro.createGraphFactory
import tech.orbitweb.orbitx.libraries.di.DependencyInjectionGraphOwner
import tech.orbitweb.orbitx.libraries.workmanager.api.di.MetroWorkerFactory
import tech.orbitweb.orbitx.di.AppGraph
import tech.orbitweb.orbitx.info.logApplicationInfo
import tech.orbitweb.orbitx.initializer.CacheCleanerInitializer
import tech.orbitweb.orbitx.initializer.CrashInitializer
import tech.orbitweb.orbitx.initializer.PlatformInitializer

class OrbitApplication : Application(), DependencyInjectionGraphOwner, Configuration.Provider {
    override val graph: AppGraph = createGraphFactory<AppGraph.Factory>().create(this)

    override val workManagerConfiguration: Configuration = Configuration.Builder()
        .setWorkerFactory(MetroWorkerFactory(graph.workerProviders))
        .build()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate() {
        super.onCreate()
        AppInitializer.getInstance(this).apply {
            initializeComponent(CrashInitializer::class.java)
            initializeComponent(PlatformInitializer::class.java)
            initializeComponent(CacheCleanerInitializer::class.java)
        }

        logApplicationInfo(this)

        tech.orbitweb.orbitx.libraries.core.crypto.OrbitEphemeralTracker.init(filesDir, cacheDir)

        // Disable the strict offset check for anchored draggable components, as it can cause issues with bottom sheets.
        // Remove once https://issuetracker.google.com/issues/477038695 is fixed.
        isAnchoredDraggableComponentsStrictOffsetCheckEnabled = false
    }
}

typealias ElementXApplication = OrbitApplication
