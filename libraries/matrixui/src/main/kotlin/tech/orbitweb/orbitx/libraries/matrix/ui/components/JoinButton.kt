/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.ui.components

import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import tech.orbitweb.orbitx.compound.theme.ElementTheme
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.ButtonSize
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.TextButton
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings

@Composable
fun JoinButton(
    showProgress: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CompositionLocalProvider(LocalContentColor provides ElementTheme.colors.textActionAccent) {
        TextButton(
            modifier = modifier,
            text = stringResource(CommonStrings.action_join),
            onClick = onClick,
            size = ButtonSize.LargeLowPadding,
            showProgress = showProgress,
        )
    }
}
