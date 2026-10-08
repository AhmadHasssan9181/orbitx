/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.designsystem.components.avatar.internal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import tech.orbitweb.orbitx.compound.theme.avatarColors
import tech.orbitweb.orbitx.libraries.designsystem.components.avatar.Avatar
import tech.orbitweb.orbitx.libraries.designsystem.components.avatar.AvatarType
import tech.orbitweb.orbitx.libraries.designsystem.components.avatar.anAvatarData
import tech.orbitweb.orbitx.libraries.designsystem.preview.ElementPreview
import tech.orbitweb.orbitx.libraries.designsystem.preview.PreviewsDayNight
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.Text

@PreviewsDayNight
@Composable
internal fun UserAvatarColorsPreview() = ElementPreview {
    Column(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        repeat(avatarColors().size) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Note: it's OK, since the hash of "0" is 0, the hash of "1" is 1, etc.
                Avatar(
                    avatarData = anAvatarData(id = "$it"),
                    avatarType = AvatarType.User,
                )
                Text(text = "Color index $it")
            }
        }
    }
}
