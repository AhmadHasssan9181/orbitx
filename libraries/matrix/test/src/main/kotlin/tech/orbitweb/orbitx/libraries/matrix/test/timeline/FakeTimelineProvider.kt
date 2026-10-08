/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.test.timeline

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.Timeline
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.TimelineProvider

class FakeTimelineProvider(
    initialTimeline: Timeline? = null,
) : TimelineProvider {
    private val timelineFlow = MutableStateFlow(initialTimeline)

    override fun activeTimelineFlow(): StateFlow<Timeline?> {
        return timelineFlow.asStateFlow()
    }
}
