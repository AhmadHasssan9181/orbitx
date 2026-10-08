/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.preferences.impl.blockedusers

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import kotlinx.collections.immutable.toImmutableList
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.matrix.api.user.MatrixUser
import tech.orbitweb.orbitx.libraries.matrix.ui.components.aMatrixUserList

class BlockedUsersStateProvider : PreviewParameterProvider<BlockedUsersState> {
    override val values: Sequence<BlockedUsersState>
        get() = sequenceOf(
            aBlockedUsersState(),
            aBlockedUsersState(blockedUsers = aMatrixUserList().map { it.copy(displayName = null, avatarUrl = null) }),
            aBlockedUsersState(blockedUsers = emptyList()),
            aBlockedUsersState(unblockUserAction = AsyncAction.ConfirmingNoParams),
            aBlockedUsersState(unblockUserAction = AsyncAction.Loading),
            aBlockedUsersState(unblockUserAction = AsyncAction.Failure(RuntimeException("Failed to unblock user"))),
            aBlockedUsersState(unblockUserAction = AsyncAction.Success(Unit)),
        )
}

internal fun aBlockedUsersState(
    blockedUsers: List<MatrixUser> = aMatrixUserList(),
    unblockUserAction: AsyncAction<Unit> = AsyncAction.Uninitialized,
    eventSink: (BlockedUsersEvents) -> Unit = {},
): BlockedUsersState {
    return BlockedUsersState(
        blockedUsers = blockedUsers.toImmutableList(),
        unblockUserAction = unblockUserAction,
        eventSink = eventSink,
    )
}
