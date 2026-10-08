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
import tech.orbitweb.orbitx.features.messages.impl.timeline.aTimelineItemReactions
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.TimelineItemGroupPosition
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemEncryptedContent
import tech.orbitweb.orbitx.libraries.designsystem.preview.ElementPreview
import tech.orbitweb.orbitx.libraries.designsystem.preview.PreviewsDayNight
import tech.orbitweb.orbitx.libraries.designsystem.preview.USER_NAME_ALICE
import tech.orbitweb.orbitx.libraries.designsystem.preview.USER_NAME_BOB
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.UnableToDecryptContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.UtdCause

@PreviewsDayNight
@Composable
internal fun TimelineItemEventRowUtdPreview() = ElementPreview {
    Column {
        ATimelineItemEventRow(
            event = aTimelineItemEvent(
                senderDisplayName = USER_NAME_ALICE,
                isMine = false,
                content = TimelineItemEncryptedContent(
                    data = UnableToDecryptContent.Data.MegolmV1AesSha2(
                        sessionId = "sessionId",
                        utdCause = UtdCause.UnsignedDevice,
                    )
                ),
                timelineItemReactions = aTimelineItemReactions(count = 0),
                groupPosition = TimelineItemGroupPosition.First,
            ),
        )
        ATimelineItemEventRow(
            event = aTimelineItemEvent(
                senderDisplayName = USER_NAME_BOB,
                isMine = false,
                content = TimelineItemEncryptedContent(
                    data = UnableToDecryptContent.Data.MegolmV1AesSha2(
                        sessionId = "sessionId",
                        utdCause = UtdCause.VerificationViolation,
                    )
                ),
                groupPosition = TimelineItemGroupPosition.First,
                timelineItemReactions = aTimelineItemReactions(count = 0)
            ),
        )

        ATimelineItemEventRow(
            event = aTimelineItemEvent(
                senderDisplayName = USER_NAME_BOB,
                isMine = false,
                content = TimelineItemEncryptedContent(
                    data = UnableToDecryptContent.Data.MegolmV1AesSha2(
                        sessionId = "sessionId",
                        utdCause = UtdCause.SentBeforeWeJoined,
                    )
                ),
                groupPosition = TimelineItemGroupPosition.Last,
                timelineItemReactions = aTimelineItemReactions(count = 0)
            ),
        )
    }
}
