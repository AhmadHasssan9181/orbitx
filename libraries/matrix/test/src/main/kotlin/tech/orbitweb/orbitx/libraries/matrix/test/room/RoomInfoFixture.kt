/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.test.room

import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.toImmutableList
import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomAlias
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.libraries.matrix.api.room.CallIntentConsensus
import tech.orbitweb.orbitx.libraries.matrix.api.room.CurrentUserMembership
import tech.orbitweb.orbitx.libraries.matrix.api.room.RoomInfo
import tech.orbitweb.orbitx.libraries.matrix.api.room.RoomMember
import tech.orbitweb.orbitx.libraries.matrix.api.room.RoomNotificationMode
import tech.orbitweb.orbitx.libraries.matrix.api.room.history.RoomHistoryVisibility
import tech.orbitweb.orbitx.libraries.matrix.api.room.join.JoinRule
import tech.orbitweb.orbitx.libraries.matrix.api.room.powerlevels.RoomPowerLevels
import tech.orbitweb.orbitx.libraries.matrix.api.room.tombstone.SuccessorRoom
import tech.orbitweb.orbitx.libraries.matrix.api.user.MatrixUser
import tech.orbitweb.orbitx.libraries.matrix.test.AN_AVATAR_URL
import tech.orbitweb.orbitx.libraries.matrix.test.A_ROOM_ID
import tech.orbitweb.orbitx.libraries.matrix.test.A_ROOM_NAME
import tech.orbitweb.orbitx.libraries.matrix.test.A_ROOM_RAW_NAME
import tech.orbitweb.orbitx.libraries.matrix.test.A_ROOM_TOPIC

fun aRoomInfo(
    id: RoomId = A_ROOM_ID,
    name: String? = A_ROOM_NAME,
    rawName: String? = A_ROOM_RAW_NAME,
    topic: String? = A_ROOM_TOPIC,
    avatarUrl: String? = AN_AVATAR_URL,
    isPublic: Boolean = true,
    isDirect: Boolean = false,
    isEncrypted: Boolean = false,
    joinRule: JoinRule? = JoinRule.Public,
    isSpace: Boolean = false,
    successorRoom: SuccessorRoom? = null,
    isFavorite: Boolean = false,
    canonicalAlias: RoomAlias? = null,
    alternativeAliases: List<RoomAlias> = emptyList(),
    currentUserMembership: CurrentUserMembership = CurrentUserMembership.JOINED,
    inviter: RoomMember? = null,
    activeMembersCount: Long = 2,
    invitedMembersCount: Long = 1,
    joinedMembersCount: Long = 1,
    highlightCount: Long = 0,
    notificationCount: Long = 0,
    userDefinedNotificationMode: RoomNotificationMode? = null,
    hasRoomCall: Boolean = false,
    roomPowerLevels: RoomPowerLevels? = RoomPowerLevels(
        values = defaultRoomPowerLevelValues(),
        users = persistentMapOf(),
    ),
    activeRoomCallParticipants: List<UserId> = emptyList(),
    heroes: List<MatrixUser> = emptyList(),
    pinnedEventIds: List<EventId> = emptyList(),
    roomCreators: List<UserId> = emptyList(),
    isMarkedUnread: Boolean = false,
    numUnreadMessages: Long = 0,
    numUnreadNotifications: Long = 0,
    numUnreadMentions: Long = 0,
    historyVisibility: RoomHistoryVisibility = RoomHistoryVisibility.Joined,
    roomVersion: String? = "11",
    privilegedCreatorRole: Boolean = false,
    isLowPriority: Boolean = false,
    activeCallIntentConsensus: CallIntentConsensus = CallIntentConsensus.None,
    isDm: Boolean = false,
    fullyReadEventId: EventId? = null,
) = RoomInfo(
    id = id,
    name = name,
    rawName = rawName,
    topic = topic,
    avatarUrl = avatarUrl,
    isPublic = isPublic,
    isDirect = isDirect,
    isEncrypted = isEncrypted,
    joinRule = joinRule,
    isSpace = isSpace,
    successorRoom = successorRoom,
    isFavorite = isFavorite,
    canonicalAlias = canonicalAlias,
    alternativeAliases = alternativeAliases.toImmutableList(),
    currentUserMembership = currentUserMembership,
    inviter = inviter,
    activeMembersCount = activeMembersCount,
    invitedMembersCount = invitedMembersCount,
    joinedMembersCount = joinedMembersCount,
    highlightCount = highlightCount,
    notificationCount = notificationCount,
    userDefinedNotificationMode = userDefinedNotificationMode,
    hasRoomCall = hasRoomCall,
    roomPowerLevels = roomPowerLevels,
    activeRoomCallParticipants = activeRoomCallParticipants.toImmutableList(),
    heroes = heroes.toImmutableList(),
    pinnedEventIds = pinnedEventIds.toImmutableList(),
    creators = roomCreators.toImmutableList(),
    isMarkedUnread = isMarkedUnread,
    numUnreadMessages = numUnreadMessages,
    numUnreadNotifications = numUnreadNotifications,
    numUnreadMentions = numUnreadMentions,
    historyVisibility = historyVisibility,
    roomVersion = roomVersion,
    privilegedCreatorRole = privilegedCreatorRole,
    isLowPriority = isLowPriority,
    activeCallIntentConsensus = activeCallIntentConsensus,
    isDm = isDm,
    fullyReadEventId = fullyReadEventId,
)
