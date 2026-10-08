/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.home.impl.model

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import tech.orbitweb.orbitx.features.invite.api.InviteData
import tech.orbitweb.orbitx.libraries.designsystem.components.avatar.AvatarData
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomAlias
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.notification.CallIntent
import tech.orbitweb.orbitx.libraries.matrix.api.room.RoomNotificationMode
import tech.orbitweb.orbitx.libraries.matrix.ui.model.InviteSender

@Immutable
data class RoomListRoomSummary(
    val id: String,
    val displayType: RoomSummaryDisplayType,
    val roomId: RoomId,
    val name: String?,
    val canonicalAlias: RoomAlias?,
    val numberOfUnreadMessages: Long,
    val numberOfUnreadMentions: Long,
    val numberOfUnreadNotifications: Long,
    val isMarkedUnread: Boolean,
    val timestamp: String?,
    val latestEvent: LatestEvent,
    val avatarData: AvatarData,
    val userDefinedNotificationMode: RoomNotificationMode?,
    val hasRoomCall: Boolean,
    val activeCallIntent: CallIntent?,
    val isDirect: Boolean,
    val isDm: Boolean,
    val isFavorite: Boolean,
    val inviteSender: InviteSender?,
    val isTombstoned: Boolean,
    val heroes: ImmutableList<AvatarData>,
    val isSpace: Boolean,
) {
    val isHighlighted = userDefinedNotificationMode != RoomNotificationMode.MUTE &&
        (numberOfUnreadNotifications > 0 || numberOfUnreadMentions > 0) ||
        isMarkedUnread

    val hasNewContent = numberOfUnreadMessages > 0 ||
        numberOfUnreadMentions > 0 ||
        numberOfUnreadNotifications > 0 ||
        isMarkedUnread

    fun toInviteData() = InviteData(
        roomId = roomId,
        roomName = name ?: roomId.value,
        isDm = isDm,
    )
}
