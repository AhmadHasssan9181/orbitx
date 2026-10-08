/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.sessionstorage.test.observer

import tech.orbitweb.orbitx.libraries.sessionstorage.api.observer.SessionListener
import tech.orbitweb.orbitx.libraries.sessionstorage.api.observer.SessionObserver
import java.util.concurrent.CopyOnWriteArraySet

class FakeSessionObserver : SessionObserver {
    private val _listeners = CopyOnWriteArraySet<SessionListener>()

    val listeners: List<SessionListener>
        get() = _listeners.toList()

    override fun addListener(listener: SessionListener) {
        _listeners.add(listener)
    }

    override fun removeListener(listener: SessionListener) {
        _listeners.remove(listener)
    }

    suspend fun onSessionCreated(userId: String) {
        listeners.forEach { it.onSessionCreated(userId) }
    }

    suspend fun onSessionDeleted(userId: String, wasLastSession: Boolean = true) {
        listeners.forEach { it.onSessionDeleted(userId, wasLastSession = wasLastSession) }
    }
}
