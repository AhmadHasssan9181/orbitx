/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.test.pinned

import kotlinx.coroutines.flow.StateFlow
import tech.orbitweb.orbitx.features.messages.api.pinned.PinnedEventsTimelineProvider
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.Timeline
import tech.orbitweb.orbitx.libraries.matrix.test.timeline.FakeTimelineProvider

class FakePinnedEventsTimelineProvider(
    private val fakeTimelineProvider: FakeTimelineProvider = FakeTimelineProvider(),
) : PinnedEventsTimelineProvider {
    override fun activeTimelineFlow(): StateFlow<Timeline?> = fakeTimelineProvider.activeTimelineFlow()
}
