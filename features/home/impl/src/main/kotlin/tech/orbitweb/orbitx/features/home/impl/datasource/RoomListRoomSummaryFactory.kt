/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.home.impl.datasource

import dev.zacsweers.metro.Inject
import kotlinx.collections.immutable.toImmutableList
import tech.orbitweb.orbitx.features.home.impl.model.LatestEvent
import tech.orbitweb.orbitx.features.home.impl.model.RoomListRoomSummary
import tech.orbitweb.orbitx.features.home.impl.model.RoomSummaryDisplayType
import tech.orbitweb.orbitx.libraries.core.extensions.orEmpty
import tech.orbitweb.orbitx.libraries.dateformatter.api.DateFormatter
import tech.orbitweb.orbitx.libraries.dateformatter.api.DateFormatterMode
import tech.orbitweb.orbitx.libraries.designsystem.components.avatar.AvatarSize
import tech.orbitweb.orbitx.libraries.eventformatter.api.RoomLatestEventFormatter
import tech.orbitweb.orbitx.libraries.matrix.api.room.CallIntentConsensus
import tech.orbitweb.orbitx.libraries.matrix.api.room.CurrentUserMembership
import tech.orbitweb.orbitx.libraries.matrix.api.roomlist.LatestEventValue
import tech.orbitweb.orbitx.libraries.matrix.api.roomlist.RoomSummary
import tech.orbitweb.orbitx.libraries.matrix.ui.model.getAvatarData
import tech.orbitweb.orbitx.libraries.matrix.ui.model.toInviteSender

@Inject
class RoomListRoomSummaryFactory(
    private val dateFormatter: DateFormatter,
    private val roomLatestEventFormatter: RoomLatestEventFormatter,
) {
    fun create(roomSummary: RoomSummary): RoomListRoomSummary {
        val roomInfo = roomSummary.info
        val avatarData = roomInfo.getAvatarData(size = AvatarSize.RoomListItem)
        return RoomListRoomSummary(
            id = roomSummary.roomId.value,
            roomId = roomSummary.roomId,
            name = roomInfo.name,
            numberOfUnreadMessages = roomInfo.numUnreadMessages,
            numberOfUnreadMentions = roomInfo.numUnreadMentions,
            numberOfUnreadNotifications = roomInfo.numUnreadNotifications,
            isMarkedUnread = roomInfo.isMarkedUnread,
            timestamp = dateFormatter.format(
                timestamp = roomSummary.latestEventTimestamp,
                mode = DateFormatterMode.TimeOrDate,
                useRelative = true,
            ),
            latestEvent = computeLatestEvent(roomSummary.latestEvent, roomInfo.isDm),
            avatarData = avatarData,
            userDefinedNotificationMode = roomInfo.userDefinedNotificationMode,
            hasRoomCall = roomInfo.hasRoomCall,
            activeCallIntent = when (val consensus = roomInfo.activeCallIntentConsensus) {
                is CallIntentConsensus.Full -> consensus.callIntent
                is CallIntentConsensus.Partial -> consensus.callIntent
                CallIntentConsensus.None -> null
            },
            isDirect = roomInfo.isDirect,
            isFavorite = roomInfo.isFavorite,
            inviteSender = roomInfo.inviter?.toInviteSender(),
            isDm = roomInfo.isDm,
            canonicalAlias = roomInfo.canonicalAlias,
            displayType = when (roomInfo.currentUserMembership) {
                CurrentUserMembership.INVITED -> {
                    RoomSummaryDisplayType.INVITE
                }
                CurrentUserMembership.KNOCKED -> {
                    RoomSummaryDisplayType.KNOCKED
                }
                else -> {
                    RoomSummaryDisplayType.ROOM
                }
            },
            heroes = roomInfo.heroes.map { user ->
                user.getAvatarData(size = AvatarSize.RoomListItem)
            }.toImmutableList(),
            isTombstoned = roomInfo.successorRoom != null,
            isSpace = roomInfo.isSpace,
        )
    }

    private fun computeLatestEvent(latestEvent: LatestEventValue, dm: Boolean): LatestEvent {
        return when (latestEvent) {
            is LatestEventValue.None -> {
                LatestEvent.None
            }
            is LatestEventValue.Local -> {
                if (latestEvent.isSending) {
                    val content = roomLatestEventFormatter.format(latestEvent, dm).orEmpty()
                    LatestEvent.Sending(
                        content = content,
                    )
                } else {
                    LatestEvent.Error
                }
            }
            is LatestEventValue.Remote -> {
                val content = roomLatestEventFormatter.format(latestEvent, dm).orEmpty()
                LatestEvent.Synced(
                    content = content,
                )
            }
            is LatestEventValue.RoomInvite -> LatestEvent.None
        }
    }
}
