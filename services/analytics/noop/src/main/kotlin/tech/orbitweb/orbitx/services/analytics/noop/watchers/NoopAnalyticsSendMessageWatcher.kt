/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.services.analytics.noop.watchers

import dev.zacsweers.metro.ContributesBinding
import tech.orbitweb.orbitx.libraries.di.RoomScope
import tech.orbitweb.orbitx.services.analytics.api.watchers.AnalyticsSendMessageWatcher

@ContributesBinding(RoomScope::class)
class NoopAnalyticsSendMessageWatcher : AnalyticsSendMessageWatcher {
    override fun start() = Unit
    override fun stop() = Unit
}
