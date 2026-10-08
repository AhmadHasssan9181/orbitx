/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.designsystem.theme.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import tech.orbitweb.orbitx.compound.theme.ElementTheme

@Composable
fun NavigationBarText(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier,
        text = text,
        style = ElementTheme.typography.fontBodySmMedium,
    )
}
