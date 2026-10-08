/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.ui.components

import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import tech.orbitweb.orbitx.compound.theme.ElementTheme
import tech.orbitweb.orbitx.compound.tokens.generated.CompoundIcons
import tech.orbitweb.orbitx.libraries.designsystem.preview.ElementPreview
import tech.orbitweb.orbitx.libraries.designsystem.preview.PreviewsDayNight
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.Icon
import tech.orbitweb.orbitx.libraries.matrix.api.spaces.SpaceRoomVisibility
import tech.orbitweb.orbitx.libraries.matrix.ui.model.icon
import tech.orbitweb.orbitx.libraries.matrix.ui.model.label
import tech.orbitweb.orbitx.libraries.ui.strings.CommonPlurals
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings

@Composable
fun SpaceInfoRow(
    leftText: String,
    rightText: String?,
    modifier: Modifier = Modifier,
    iconVector: ImageVector? = null,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (iconVector != null) {
            Icon(
                modifier = Modifier.size(16.dp),
                imageVector = iconVector,
                contentDescription = null,
                tint = ElementTheme.colors.iconTertiary,
            )
        }
        val text = if (rightText != null) {
            stringResource(id = CommonStrings.screen_space_list_details, leftText, rightText)
        } else {
            leftText
        }
        Text(
            text = text,
            style = ElementTheme.typography.fontBodyMdRegular,
            color = ElementTheme.colors.textSecondary,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun SpaceInfoRow(
    visibility: SpaceRoomVisibility,
    modifier: Modifier = Modifier,
) {
    SpaceInfoRow(
        leftText = visibility.label,
        rightText = null,
        modifier = modifier,
        iconVector = visibility.icon,
    )
}

@Composable
@ReadOnlyComposable
fun numberOfRooms(numberOfRooms: Int): String {
    return pluralStringResource(CommonPlurals.common_rooms, numberOfRooms, numberOfRooms)
}

@Composable
@ReadOnlyComposable
fun numberOfSpaces(numberOfSpaces: Int): String {
    return pluralStringResource(CommonPlurals.common_spaces, numberOfSpaces, numberOfSpaces)
}

@PreviewsDayNight
@Composable
internal fun SpaceInfoRowPreview() = ElementPreview {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SpaceInfoRow(
            leftText = numberOfSpaces(5),
            rightText = numberOfRooms(10),
        )
        SpaceInfoRow(
            leftText = "Orbit space",
            rightText = numberOfRooms(16),
            iconVector = CompoundIcons.Space(),
        )
        SpaceInfoRow(
            visibility = SpaceRoomVisibility.Private,
        )
        SpaceInfoRow(
            visibility = SpaceRoomVisibility.Public
        )
        SpaceInfoRow(
            visibility = SpaceRoomVisibility.SpaceMembers
        )
    }
}
