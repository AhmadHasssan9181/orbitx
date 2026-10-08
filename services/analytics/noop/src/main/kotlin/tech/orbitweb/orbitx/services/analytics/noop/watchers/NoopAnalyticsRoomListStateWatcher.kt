/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.services.analytics.noop.watchers

import dev.zacsweers.metro.ContributesBinding
import tech.orbitweb.orbitx.libraries.di.SessionScope
import tech.orbitweb.orbitx.services.analytics.api.watchers.AnalyticsRoomListStateWatcher

@ContributesBinding(SessionScope::class)
class NoopAnalyticsRoomListStateWatcher : AnalyticsRoomListStateWatcher {
    override fun start() {}
    override fun stop() {}
}
