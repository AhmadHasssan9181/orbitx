/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.appnav.di

import tech.orbitweb.orbitx.features.messages.api.pinned.PinnedEventsTimelineProvider
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.TimelineProvider
import tech.orbitweb.orbitx.services.analytics.api.watchers.AnalyticsSendMessageWatcher

interface TimelineBindings {
    val timelineProvider: TimelineProvider
    val pinnedEventsTimelineProvider: PinnedEventsTimelineProvider
    val analyticsSendMessageWatcher: AnalyticsSendMessageWatcher
}
