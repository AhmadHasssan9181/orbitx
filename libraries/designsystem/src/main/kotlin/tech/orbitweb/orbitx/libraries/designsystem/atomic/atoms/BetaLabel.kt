/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.designsystem.atomic.atoms

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import tech.orbitweb.orbitx.compound.theme.ElementTheme
import tech.orbitweb.orbitx.libraries.designsystem.preview.ElementPreview
import tech.orbitweb.orbitx.libraries.designsystem.preview.PreviewsDayNight
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.Text
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings

@Composable
fun BetaLabel(
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(size = 6.dp)
    Text(
        modifier = modifier
            .border(
                width = 1.dp,
                color = ElementTheme.colors.borderInfoSubtle,
                shape = shape,
            )
            .background(
                color = ElementTheme.colors.bgInfoSubtle,
                shape = shape,
            )
            .padding(horizontal = 8.dp, vertical = 4.dp),
        text = stringResource(CommonStrings.common_beta).uppercase(),
        style = ElementTheme.typography.fontBodySmMedium,
        color = ElementTheme.colors.textInfoPrimary,
    )
}

@PreviewsDayNight
@Composable
internal fun BetaLabelPreview() = ElementPreview {
    BetaLabel()
}
