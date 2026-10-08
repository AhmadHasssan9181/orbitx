/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.di

import android.content.Context
import androidx.work.ListenableWorker
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Multibinds
import dev.zacsweers.metro.Provides
import tech.orbitweb.orbitx.libraries.architecture.NodeFactoriesBindings
import tech.orbitweb.orbitx.libraries.di.annotations.ApplicationContext
import tech.orbitweb.orbitx.libraries.workmanager.api.di.MetroWorkerFactory
import kotlin.reflect.KClass

@DependencyGraph(AppScope::class)
interface AppGraph : NodeFactoriesBindings {
    val sessionGraphFactory: SessionGraph.Factory

    @Multibinds
    val workerProviders:
        Map<KClass<out ListenableWorker>, MetroWorkerFactory.WorkerInstanceFactory<*>>

    @DependencyGraph.Factory
    interface Factory {
        fun create(
            @ApplicationContext @Provides
            context: Context
        ): AppGraph
    }
}
