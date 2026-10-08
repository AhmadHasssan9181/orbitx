/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.timeline.protection

import tech.orbitweb.orbitx.features.messages.impl.timeline.model.TimelineItem
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemAttachmentsContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemAudioContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemEmoteContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemEncryptedContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemFileContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemGalleryContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemImageContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemLegacyCallInviteContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemLocationContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemNoticeContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemPollContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemProfileChangeContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemRedactedContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemRoomMembershipContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemRtcNotificationContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemStateEventContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemStickerContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemTextContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemUnknownContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemVideoContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemVoiceContent

/**
 * Return true if the event must be hidden by default when the setting to hide images and videos is enabled.
 */
fun TimelineItem.mustBeProtected(): Boolean {
    return when (this) {
        is TimelineItem.Event -> {
            if (isMine) {
                false
            } else {
                when (content) {
                    is TimelineItemImageContent,
                    is TimelineItemGalleryContent,
                    is TimelineItemAttachmentsContent,
                    is TimelineItemVideoContent,
                    is TimelineItemStickerContent -> true
                    is TimelineItemAudioContent,
                    is TimelineItemRtcNotificationContent,
                    is TimelineItemEncryptedContent,
                    is TimelineItemFileContent,
                    TimelineItemLegacyCallInviteContent,
                    is TimelineItemLocationContent,
                    is TimelineItemPollContent,
                    TimelineItemRedactedContent,
                    is TimelineItemProfileChangeContent,
                    is TimelineItemRoomMembershipContent,
                    is TimelineItemStateEventContent,
                    is TimelineItemEmoteContent,
                    is TimelineItemNoticeContent,
                    is TimelineItemTextContent,
                    TimelineItemUnknownContent,
                    is TimelineItemVoiceContent -> false
                }
            }
        }
        is TimelineItem.Virtual -> false
        is TimelineItem.GroupedEvents -> false
    }
}
