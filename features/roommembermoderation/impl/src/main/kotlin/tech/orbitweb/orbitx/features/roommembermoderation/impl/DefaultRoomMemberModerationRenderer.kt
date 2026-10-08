/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.roommembermoderation.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import dev.zacsweers.metro.ContributesBinding
import tech.orbitweb.orbitx.features.roommembermoderation.api.ModerationAction
import tech.orbitweb.orbitx.features.roommembermoderation.api.RoomMemberModerationRenderer
import tech.orbitweb.orbitx.features.roommembermoderation.api.RoomMemberModerationState
import tech.orbitweb.orbitx.libraries.di.RoomScope
import tech.orbitweb.orbitx.libraries.matrix.api.user.MatrixUser
import timber.log.Timber

@ContributesBinding(RoomScope::class)
class DefaultRoomMemberModerationRenderer : RoomMemberModerationRenderer {
    @Composable
    override fun Render(
        state: RoomMemberModerationState,
        onSelectAction: (ModerationAction, MatrixUser) -> Unit,
        onAvatarClick: ((MatrixUser) -> Unit)?,
        modifier: Modifier
    ) {
        if (state is InternalRoomMemberModerationState) {
            RoomMemberModerationView(modifier = modifier, state = state, onSelectAction = onSelectAction, onAvatarClick = onAvatarClick)
        } else {
            SideEffect {
                Timber.d("RoomMemberModerationRenderer: Render called with unsupported state: $state")
            }
        }
    }
}
