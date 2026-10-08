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
import tech.orbitweb.orbitx.libraries.designsystem.colors.AvatarColorsProvider
import tech.orbitweb.orbitx.libraries.designsystem.components.avatar.AvatarData

@Composable
internal fun InitialLetterAvatar(
    avatarData: AvatarData,
    avatarShape: Shape,
    forcedAvatarSize: Dp?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
) {
    val avatarColors = AvatarColorsProvider.provide(avatarData.id)
    TextAvatar(
        text = avatarData.initialLetter,
        size = forcedAvatarSize ?: avatarData.size.dp,
        avatarShape = avatarShape,
        colors = avatarColors,
        contentDescription = contentDescription,
        modifier = modifier
    )
}
