/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.timeline.factories.event

import dev.zacsweers.metro.Inject
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemEventContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemUnknownContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.FailedToParseMessageLikeContent

@Inject
class TimelineItemContentFailedToParseMessageFactory {
    fun create(@Suppress("UNUSED_PARAMETER") failedToParseMessageLike: FailedToParseMessageLikeContent): TimelineItemEventContent {
        return TimelineItemUnknownContent
    }
}
