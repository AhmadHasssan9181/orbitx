/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.roommembermoderation.api

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import tech.orbitweb.orbitx.libraries.matrix.api.user.MatrixUser

interface RoomMemberModerationRenderer {
    @Composable
    fun Render(
        state: RoomMemberModerationState,
        onSelectAction: (ModerationAction, MatrixUser) -> Unit,
        onAvatarClick: ((MatrixUser) -> Unit)?,
        modifier: Modifier,
    )
}
