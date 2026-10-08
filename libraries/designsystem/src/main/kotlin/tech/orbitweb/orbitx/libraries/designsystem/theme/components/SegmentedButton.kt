/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.designsystem.theme.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import tech.orbitweb.orbitx.compound.theme.ElementTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleChoiceSegmentedButtonRowScope.SegmentedButton(
    index: Int,
    count: Int,
    selected: Boolean,
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    enabled: Boolean = true,
) {
    SegmentedButton(
        selected = selected,
        onClick = onClick,
        modifier = modifier,
        interactionSource = interactionSource,
        enabled = enabled,
        shape = SegmentedButtonDefaults.itemShape(index = index, count = count),
        label = {
            Text(
                text = text,
                style = ElementTheme.typography.fontBodyMdMedium,
            )
        },
        colors = SegmentedButtonDefaults.colors(
            activeContainerColor = ElementTheme.materialColors.primary,
            activeContentColor = ElementTheme.materialColors.onPrimary,
            activeBorderColor = ElementTheme.materialColors.primary,
            inactiveContainerColor = ElementTheme.materialColors.surface,
            inactiveContentColor = ElementTheme.materialColors.onSurface,
            inactiveBorderColor = ElementTheme.materialColors.primary,
            disabledActiveContainerColor = ElementTheme.colors.bgActionPrimaryDisabled,
            disabledActiveContentColor = ElementTheme.colors.textOnSolidPrimary,
            disabledActiveBorderColor = ElementTheme.colors.bgActionPrimaryDisabled,
            disabledInactiveContainerColor = ElementTheme.materialColors.surface,
            disabledInactiveContentColor = ElementTheme.colors.textDisabled,
            disabledInactiveBorderColor = Color.Transparent,
        )
    )
}
