/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.services.appnavstate.impl.initializer

import android.content.Context
import androidx.lifecycle.ProcessLifecycleInitializer
import androidx.startup.Initializer
import tech.orbitweb.orbitx.services.appnavstate.api.AppForegroundStateService
import tech.orbitweb.orbitx.services.appnavstate.impl.DefaultAppForegroundStateService

class AppForegroundStateServiceInitializer : Initializer<AppForegroundStateService> {
    override fun create(context: Context): AppForegroundStateService {
        return DefaultAppForegroundStateService()
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf(
        ProcessLifecycleInitializer::class.java
    )
}
