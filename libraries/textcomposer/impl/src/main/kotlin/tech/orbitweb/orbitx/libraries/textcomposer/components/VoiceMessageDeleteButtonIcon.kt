/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.textcomposer.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import tech.orbitweb.orbitx.compound.theme.ElementTheme
import tech.orbitweb.orbitx.compound.tokens.generated.CompoundIcons
import tech.orbitweb.orbitx.libraries.designsystem.preview.ElementPreview
import tech.orbitweb.orbitx.libraries.designsystem.preview.PreviewsDayNight
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.Icon
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.IconButton
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings

@Composable
fun VoiceMessageDeleteButtonIcon(
    enabled: Boolean,
    modifier: Modifier = Modifier,
) {
    Icon(
        modifier = modifier.size(24.dp),
        imageVector = CompoundIcons.Delete(),
        contentDescription = stringResource(CommonStrings.a11y_delete),
        tint = if (enabled) {
            ElementTheme.colors.iconCriticalPrimary
        } else {
            ElementTheme.colors.iconDisabled
        },
    )
}

@PreviewsDayNight
@Composable
internal fun VoiceMessageDeleteButtonIconPreview() = ElementPreview {
    Row {
        IconButton(onClick = {}) {
            VoiceMessageDeleteButtonIcon(
                enabled = true,
            )
        }
        IconButton(onClick = {}) {
            VoiceMessageDeleteButtonIcon(
                enabled = false,
            )
        }
    }
}
