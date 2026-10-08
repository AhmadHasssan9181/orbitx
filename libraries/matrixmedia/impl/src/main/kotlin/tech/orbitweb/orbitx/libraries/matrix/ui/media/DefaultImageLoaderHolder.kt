/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.ui.media

import coil3.ImageLoader
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.SingleIn
import tech.orbitweb.orbitx.libraries.matrix.api.MatrixClient
import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId
import tech.orbitweb.orbitx.libraries.sessionstorage.api.observer.SessionListener
import tech.orbitweb.orbitx.libraries.sessionstorage.api.observer.SessionObserver

@ContributesBinding(AppScope::class)
@SingleIn(AppScope::class)
class DefaultImageLoaderHolder(
    private val imageLoaderFactory: ImageLoaderFactory,
    private val sessionObserver: SessionObserver,
) : ImageLoaderHolder {
    private val map = mutableMapOf<SessionId, ImageLoader>()
    private val notLoggedInImageLoader by lazy {
        imageLoaderFactory.newImageLoader()
    }

    init {
        observeSessions()
    }

    private fun observeSessions() {
        sessionObserver.addListener(object : SessionListener {
            override suspend fun onSessionDeleted(userId: String, wasLastSession: Boolean) {
                remove(SessionId(userId))
            }
        })
    }

    override fun get(): ImageLoader {
        return notLoggedInImageLoader
    }

    override fun get(client: MatrixClient): ImageLoader {
        return synchronized(map) {
            map.getOrPut(client.sessionId) {
                imageLoaderFactory
                    .newImageLoader(client.matrixMediaLoader)
            }
        }
    }

    override fun remove(sessionId: SessionId) {
        synchronized(map) {
            map.remove(sessionId)
        }
    }
}
