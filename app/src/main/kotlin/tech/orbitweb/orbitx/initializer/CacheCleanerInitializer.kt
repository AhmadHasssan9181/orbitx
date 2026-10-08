/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.initializer

import android.content.Context
import androidx.startup.Initializer
import tech.orbitweb.orbitx.features.cachecleaner.impl.CacheCleanerBindings
import tech.orbitweb.orbitx.libraries.architecture.bindings

class CacheCleanerInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        context.bindings<CacheCleanerBindings>().cacheCleaner().clearCache()
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
