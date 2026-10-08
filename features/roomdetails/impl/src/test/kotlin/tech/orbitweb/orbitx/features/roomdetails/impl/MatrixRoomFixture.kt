/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.roomdetails.impl

import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomAlias
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.libraries.matrix.api.room.RoomMember
import tech.orbitweb.orbitx.libraries.matrix.api.room.join.JoinRule
import tech.orbitweb.orbitx.libraries.matrix.api.room.powerlevels.RoomPermissions
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.ReceiptType
import tech.orbitweb.orbitx.libraries.matrix.test.AN_AVATAR_URL
import tech.orbitweb.orbitx.libraries.matrix.test.A_ROOM_ALIAS
import tech.orbitweb.orbitx.libraries.matrix.test.A_ROOM_ID
import tech.orbitweb.orbitx.libraries.matrix.test.A_ROOM_NAME
import tech.orbitweb.orbitx.libraries.matrix.test.A_ROOM_TOPIC
import tech.orbitweb.orbitx.libraries.matrix.test.A_SESSION_ID
import tech.orbitweb.orbitx.libraries.matrix.test.notificationsettings.FakeNotificationSettingsService
import tech.orbitweb.orbitx.libraries.matrix.test.room.FakeBaseRoom
import tech.orbitweb.orbitx.libraries.matrix.test.room.FakeJoinedRoom
import tech.orbitweb.orbitx.libraries.matrix.test.room.aRoomInfo
import tech.orbitweb.orbitx.libraries.matrix.test.room.powerlevels.FakeRoomPermissions
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError

fun aFakeBaseRoom(
    sessionId: SessionId = A_SESSION_ID,
    roomId: RoomId = A_ROOM_ID,
    displayName: String = A_ROOM_NAME,
    rawName: String? = displayName,
    topic: String? = A_ROOM_TOPIC,
    avatarUrl: String? = AN_AVATAR_URL,
    canonicalAlias: RoomAlias? = A_ROOM_ALIAS,
    roomPermissions: RoomPermissions = FakeRoomPermissions(),
    isEncrypted: Boolean = true,
    isPublic: Boolean = true,
    isDirect: Boolean = false,
    joinRule: JoinRule? = null,
    activeMemberCount: Long = 1,
    joinedMemberCount: Long = 1,
    invitedMemberCount: Long = 0,
    userDisplayNameResult: (UserId) -> Result<String?> = { lambdaError() },
    userAvatarUrlResult: () -> Result<String?> = { lambdaError() },
    getUpdatedMemberResult: (UserId) -> Result<RoomMember> = { lambdaError() },
    userRoleResult: () -> Result<RoomMember.Role> = { lambdaError() },
    setIsFavoriteResult: (Boolean) -> Result<Unit> = { lambdaError() },
    markAsReadResult: (ReceiptType) -> Result<Unit> = { lambdaError() },
) = FakeBaseRoom(
    sessionId = sessionId,
    roomId = roomId,
    userDisplayNameResult = userDisplayNameResult,
    userAvatarUrlResult = userAvatarUrlResult,
    getUpdatedMemberResult = getUpdatedMemberResult,
    userRoleResult = userRoleResult,
    setIsFavoriteResult = setIsFavoriteResult,
    markAsReadResult = markAsReadResult,
    roomPermissions = roomPermissions,
    initialRoomInfo = aRoomInfo(
        name = displayName,
        rawName = rawName,
        topic = topic,
        avatarUrl = avatarUrl,
        canonicalAlias = canonicalAlias,
        isDirect = isDirect,
        isPublic = isPublic,
        isEncrypted = isEncrypted,
        joinRule = joinRule,
        joinedMembersCount = joinedMemberCount,
        activeMembersCount = activeMemberCount,
        invitedMembersCount = invitedMemberCount,
    )
)

fun aJoinedRoom(
    sessionId: SessionId = A_SESSION_ID,
    roomId: RoomId = A_ROOM_ID,
    displayName: String = A_ROOM_NAME,
    rawName: String? = displayName,
    topic: String? = A_ROOM_TOPIC,
    avatarUrl: String? = AN_AVATAR_URL,
    canonicalAlias: RoomAlias? = A_ROOM_ALIAS,
    roomPermissions: RoomPermissions = FakeRoomPermissions(),
    isEncrypted: Boolean = true,
    isPublic: Boolean = true,
    isDirect: Boolean = false,
    joinRule: JoinRule? = null,
    activeMemberCount: Long = 1,
    joinedMemberCount: Long = 1,
    invitedMemberCount: Long = 0,
    notificationSettingsService: FakeNotificationSettingsService = FakeNotificationSettingsService(),
    userDisplayNameResult: (UserId) -> Result<String?> = { lambdaError() },
    userAvatarUrlResult: () -> Result<String?> = { lambdaError() },
    setNameResult: (String) -> Result<Unit> = { lambdaError() },
    setTopicResult: (String) -> Result<Unit> = { lambdaError() },
    updateAvatarResult: (String, ByteArray) -> Result<Unit> = { _, _ -> lambdaError() },
    removeAvatarResult: () -> Result<Unit> = { lambdaError() },
    getUpdatedMemberResult: (UserId) -> Result<RoomMember> = { lambdaError() },
    userRoleResult: () -> Result<RoomMember.Role> = { lambdaError() },
    kickUserResult: (UserId, String?) -> Result<Unit> = { _, _ -> lambdaError() },
    banUserResult: (UserId, String?) -> Result<Unit> = { _, _ -> lambdaError() },
    unBanUserResult: (UserId, String?) -> Result<Unit> = { _, _ -> lambdaError() },
    updateCanonicalAliasResult: (RoomAlias?, List<RoomAlias>) -> Result<Unit> = { _, _ -> lambdaError() },
    publishRoomAliasInRoomDirectoryResult: (RoomAlias) -> Result<Boolean> = { lambdaError() },
    removeRoomAliasFromRoomDirectoryResult: (RoomAlias) -> Result<Boolean> = { lambdaError() },
    setIsFavoriteResult: (Boolean) -> Result<Unit> = { lambdaError() },
    markAsReadResult: (ReceiptType) -> Result<Unit> = { lambdaError() },
) = FakeJoinedRoom(
    roomNotificationSettingsService = notificationSettingsService,
    setNameResult = setNameResult,
    setTopicResult = setTopicResult,
    updateAvatarResult = updateAvatarResult,
    removeAvatarResult = removeAvatarResult,
    kickUserResult = kickUserResult,
    banUserResult = banUserResult,
    unBanUserResult = unBanUserResult,
    updateCanonicalAliasResult = updateCanonicalAliasResult,
    publishRoomAliasInRoomDirectoryResult = publishRoomAliasInRoomDirectoryResult,
    removeRoomAliasFromRoomDirectoryResult = removeRoomAliasFromRoomDirectoryResult,
    baseRoom = aFakeBaseRoom(
        sessionId = sessionId,
        roomId = roomId,
        roomPermissions = roomPermissions,
        userDisplayNameResult = userDisplayNameResult,
        userAvatarUrlResult = userAvatarUrlResult,
        getUpdatedMemberResult = getUpdatedMemberResult,
        userRoleResult = userRoleResult,
        setIsFavoriteResult = setIsFavoriteResult,
        displayName = displayName,
        rawName = rawName,
        topic = topic,
        avatarUrl = avatarUrl,
        canonicalAlias = canonicalAlias,
        isDirect = isDirect,
        isPublic = isPublic,
        isEncrypted = isEncrypted,
        joinRule = joinRule,
        joinedMemberCount = joinedMemberCount,
        activeMemberCount = activeMemberCount,
        invitedMemberCount = invitedMemberCount,
        markAsReadResult = markAsReadResult,
    )
)
