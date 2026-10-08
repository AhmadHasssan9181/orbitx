/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.timeline.di

import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemLocationContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemVoiceContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.ensureActiveLiveLocation
import tech.orbitweb.orbitx.libraries.architecture.Presenter
import tech.orbitweb.orbitx.libraries.voiceplayer.api.VoiceMessageState
import tech.orbitweb.orbitx.libraries.voiceplayer.api.aVoiceMessageState

/**
 * A fake [TimelineItemPresenterFactories] for screenshot tests.
 */
fun aFakeTimelineItemPresenterFactories() = TimelineItemPresenterFactories(
    mapOf(
        Pair(
            TimelineItemLocationContent::class,
            TimelineItemPresenterFactory<TimelineItemLocationContent, TimelineItemLocationContent> { content ->
                Presenter { content.ensureActiveLiveLocation() }
            },
        ),
        Pair(
            TimelineItemVoiceContent::class,
            TimelineItemPresenterFactory<TimelineItemVoiceContent, VoiceMessageState> { Presenter { aVoiceMessageState() } },
        ),
    )
)
