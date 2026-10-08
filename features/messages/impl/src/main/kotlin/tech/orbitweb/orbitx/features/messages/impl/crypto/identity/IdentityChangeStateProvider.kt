/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.crypto.identity

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import tech.orbitweb.orbitx.libraries.designsystem.components.avatar.AvatarData
import tech.orbitweb.orbitx.libraries.designsystem.components.avatar.AvatarSize
import tech.orbitweb.orbitx.libraries.designsystem.preview.USER_NAME_ALICE
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.libraries.matrix.api.encryption.identity.IdentityState
import tech.orbitweb.orbitx.libraries.matrix.ui.room.IdentityRoomMember
import tech.orbitweb.orbitx.libraries.matrix.ui.room.RoomMemberIdentityStateChange
import kotlinx.collections.immutable.toImmutableList

class IdentityChangeStateProvider : PreviewParameterProvider<IdentityChangeState> {
    override val values: Sequence<IdentityChangeState>
        get() = sequenceOf(
            anIdentityChangeState(),
            anIdentityChangeState(
                roomMemberIdentityStateChanges = listOf(
                    aRoomMemberIdentityStateChange(
                        identityRoomMember = anIdentityRoomMember(),
                        identityState = IdentityState.PinViolation,
                    ),
                ),
            ),
            anIdentityChangeState(
                roomMemberIdentityStateChanges = listOf(
                    aRoomMemberIdentityStateChange(
                        identityRoomMember = anIdentityRoomMember(displayNameOrDefault = USER_NAME_ALICE),
                        identityState = IdentityState.VerificationViolation,
                    ),
                ),
            ),
        )
}

internal fun aRoomMemberIdentityStateChange(
    identityRoomMember: IdentityRoomMember = anIdentityRoomMember(),
    identityState: IdentityState = IdentityState.PinViolation,
) = RoomMemberIdentityStateChange(
    identityRoomMember = identityRoomMember,
    identityState = identityState,
)

internal fun anIdentityChangeState(
    roomMemberIdentityStateChanges: List<RoomMemberIdentityStateChange> = emptyList(),
    eventSink: (IdentityChangeEvent) -> Unit = {},
) = IdentityChangeState(
    roomMemberIdentityStateChanges = roomMemberIdentityStateChanges.toImmutableList(),
    eventSink = eventSink,
)

internal fun anIdentityRoomMember(
    userId: UserId = UserId("@alice:example.com"),
    displayNameOrDefault: String = userId.extractedDisplayName,
    avatarData: AvatarData = AvatarData(
        id = userId.value,
        name = null,
        url = null,
        size = AvatarSize.ComposerAlert,
    ),
) = IdentityRoomMember(
    userId = userId,
    displayNameOrDefault = displayNameOrDefault,
    avatarData = avatarData,
)
