/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.sessionstorage.test.observer

import tech.orbitweb.orbitx.libraries.sessionstorage.api.observer.SessionListener
import tech.orbitweb.orbitx.libraries.sessionstorage.api.observer.SessionObserver

class NoOpSessionObserver : SessionObserver {
    override fun addListener(listener: SessionListener) = Unit
    override fun removeListener(listener: SessionListener) = Unit
}
