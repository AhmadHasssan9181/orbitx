/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.designsystem.components.avatar.internal

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import tech.orbitweb.orbitx.libraries.designsystem.components.avatar.AvatarData

@Composable
internal fun InitialOrImageAvatar(
    avatarData: AvatarData,
    hideAvatarImage: Boolean,
    forcedAvatarSize: Dp?,
    avatarShape: Shape,
    contentDescription: String?,
    modifier: Modifier = Modifier,
) {
    when {
        avatarData.url.isNullOrBlank() || hideAvatarImage -> InitialLetterAvatar(
            avatarData = avatarData,
            avatarShape = avatarShape,
            forcedAvatarSize = forcedAvatarSize,
            modifier = modifier,
            contentDescription = contentDescription,
        )
        else -> ImageAvatar(
            avatarData = avatarData,
            avatarShape = avatarShape,
            forcedAvatarSize = forcedAvatarSize,
            modifier = modifier,
            contentDescription = contentDescription,
        )
    }
}
