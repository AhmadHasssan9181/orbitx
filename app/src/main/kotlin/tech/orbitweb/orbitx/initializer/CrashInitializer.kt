/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.initializer

import android.content.Context
import androidx.startup.Initializer
import tech.orbitweb.orbitx.features.rageshake.impl.crash.VectorUncaughtExceptionHandler
import tech.orbitweb.orbitx.features.rageshake.impl.di.RageshakeBindings
import tech.orbitweb.orbitx.libraries.architecture.bindings

class CrashInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        VectorUncaughtExceptionHandler(
            context.bindings<RageshakeBindings>().preferencesCrashDataStore(),
        ).activate()
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
