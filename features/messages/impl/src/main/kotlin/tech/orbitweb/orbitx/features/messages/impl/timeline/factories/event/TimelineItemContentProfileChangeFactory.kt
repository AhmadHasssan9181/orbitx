/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.timeline.factories.event

import dev.zacsweers.metro.Inject
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemEventContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemProfileChangeContent
import tech.orbitweb.orbitx.libraries.core.extensions.orEmpty
import tech.orbitweb.orbitx.libraries.eventformatter.api.TimelineEventFormatter
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.EventContent

@Inject
class TimelineItemContentProfileChangeFactory(
    private val timelineEventFormatter: TimelineEventFormatter,
) {
    fun create(content: EventContent, isOutgoing: Boolean, sender: UserId, senderDisambiguatedDisplayName: String): TimelineItemEventContent {
        val text = timelineEventFormatter.format(content, isOutgoing, sender, senderDisambiguatedDisplayName)
        return TimelineItemProfileChangeContent(text.orEmpty().toString())
    }
}
