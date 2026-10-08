/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.timeline.components

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import tech.orbitweb.orbitx.features.messages.impl.timeline.aTimelineItemEvent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.TimelineItem
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.aTimelineItemRedactedContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.aTimelineItemTextContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.LocalEventSendState
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.MessageShield

class TimelineItemEventForTimestampViewProvider : PreviewParameterProvider<TimelineItem.Event> {
    override val values: Sequence<TimelineItem.Event>
        get() = sequenceOf(
            aTimelineItemEvent(),
            aTimelineItemEvent().copy(localSendState = LocalEventSendState.Sending.Event),
            aTimelineItemEvent().copy(localSendState = LocalEventSendState.Failed.Unknown("AN_ERROR")),
            // Edited
            aTimelineItemEvent().copy(content = aTimelineItemTextContent().copy(isEdited = true)),
            // Sending failed + Edited (not sure this is possible IRL, but should be covered by test)
            aTimelineItemEvent().copy(
                localSendState = LocalEventSendState.Failed.Unknown("AN_ERROR"),
                content = aTimelineItemTextContent().copy(isEdited = true),
            ),
            aTimelineItemEvent(
                messageShield = MessageShield.AuthenticityNotGuaranteed(isCritical = false),
            ),
            aTimelineItemEvent(
                messageShield = MessageShield.UnknownDevice(isCritical = true),
            ),
            aTimelineItemEvent(
                content = aTimelineItemRedactedContent(),
                messageShield = MessageShield.SentInClear(isCritical = true),
            ),
        )
}
