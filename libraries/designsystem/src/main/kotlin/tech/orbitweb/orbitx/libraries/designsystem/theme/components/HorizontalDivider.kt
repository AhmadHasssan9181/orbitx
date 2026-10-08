/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.designsystem.theme.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import tech.orbitweb.orbitx.compound.theme.ElementTheme
import tech.orbitweb.orbitx.libraries.designsystem.preview.ElementThemedPreview
import tech.orbitweb.orbitx.libraries.designsystem.preview.PreviewGroup

@Composable
fun HorizontalDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = ElementDividerDefaults.thickness,
    color: Color = ElementDividerDefaults.color,
) {
    androidx.compose.material3.HorizontalDivider(
        modifier = modifier,
        thickness = thickness,
        color = color,
    )
}

object ElementDividerDefaults {
    val thickness = 1.dp
    val color
        @Composable get() = ElementTheme.colors.separatorSecondary
}

@Preview(group = PreviewGroup.Dividers)
@Composable
internal fun HorizontalDividerPreview() = ElementThemedPreview {
    Box(Modifier.padding(vertical = 10.dp), contentAlignment = Alignment.Center) {
        HorizontalDivider()
    }
}
