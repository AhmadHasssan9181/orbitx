/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.timeline.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import tech.orbitweb.orbitx.features.messages.impl.timeline.aTimelineItemEvent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.TimelineItemGroupPosition
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.aTimelineItemImageContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.aTimelineItemTextContent
import tech.orbitweb.orbitx.libraries.designsystem.preview.ElementPreview
import tech.orbitweb.orbitx.libraries.designsystem.preview.PreviewsDayNight
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.MessageShield

@PreviewsDayNight
@Composable
internal fun TimelineItemEventRowShieldPreview() = ElementPreview {
    Column {
        ATimelineItemEventRow(
            event = aTimelineItemEvent(
                senderDisplayName = "Sender with a super long name that should ellipsize",
                isMine = true,
                content = aTimelineItemTextContent(
                    body = "Message sent from unsigned device"
                ),
                groupPosition = TimelineItemGroupPosition.First,
                messageShield = aCriticalShield()
            ),
        )
        ATimelineItemEventRow(
            event = aTimelineItemEvent(
                senderDisplayName = "Sender with a super long name that should ellipsize",
                content = aTimelineItemTextContent(
                    body = "Short Message with authenticity warning"
                ),
                groupPosition = TimelineItemGroupPosition.Middle,
                messageShield = aWarningShield()
            ),
        )
        ATimelineItemEventRow(
            event = aTimelineItemEvent(
                isMine = true,
                content = aTimelineItemImageContent(
                    aspectRatio = 2.5f
                ),
                groupPosition = TimelineItemGroupPosition.Last,
                messageShield = aCriticalShield()
            ),
        )
        ATimelineItemEventRow(
            event = aTimelineItemEvent(
                content = aTimelineItemImageContent(
                    aspectRatio = 2.5f
                ),
                groupPosition = TimelineItemGroupPosition.Last,
                messageShield = aWarningShield()
            ),
        )
    }
}

private fun aWarningShield() = MessageShield.AuthenticityNotGuaranteed(isCritical = false)

internal fun aCriticalShield() = MessageShield.UnverifiedIdentity(isCritical = true)
