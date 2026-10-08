/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.cachecleaner.impl

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import tech.orbitweb.orbitx.features.cachecleaner.api.CacheCleaner
import tech.orbitweb.orbitx.libraries.core.coroutine.CoroutineDispatchers
import tech.orbitweb.orbitx.libraries.core.extensions.runCatchingExceptions
import tech.orbitweb.orbitx.libraries.di.CacheDirectory
import tech.orbitweb.orbitx.libraries.di.annotations.AppCoroutineScope
import timber.log.Timber
import java.io.File

/**
 * Default implementation of [CacheCleaner].
 */
@ContributesBinding(AppScope::class)
class DefaultCacheCleaner(
    @AppCoroutineScope
    private val coroutineScope: CoroutineScope,
    private val dispatchers: CoroutineDispatchers,
    @CacheDirectory private val cacheDir: File,
) : CacheCleaner {
    companion object {
        val SUBDIRS_TO_CLEANUP = listOf("temp/media", "temp/voice")
    }

    override fun clearCache() {
        coroutineScope.launch(dispatchers.io) {
            runCatchingExceptions {
                SUBDIRS_TO_CLEANUP.forEach {
                    File(cacheDir.path, it).apply {
                        if (exists()) {
                            if (!deleteRecursively()) error("Failed to delete recursively cache directory $this")
                        }
                        if (!mkdirs()) error("Failed to create cache directory $this")
                    }
                }
            }.onFailure {
                Timber.e(it, "Failed to clear cache")
            }
        }
    }
}
