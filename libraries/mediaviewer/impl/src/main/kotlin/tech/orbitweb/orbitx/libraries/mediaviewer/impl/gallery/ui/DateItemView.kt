/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.mediaviewer.impl.gallery.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import tech.orbitweb.orbitx.compound.theme.ElementTheme
import tech.orbitweb.orbitx.libraries.designsystem.preview.ElementPreview
import tech.orbitweb.orbitx.libraries.designsystem.preview.PreviewsDayNight
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.Text
import tech.orbitweb.orbitx.libraries.mediaviewer.impl.model.MediaItem

@Composable
fun DateItemView(
    item: MediaItem.DateSeparator,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp)
            .semantics {
                heading()
            },
        text = item.formattedDate,
        textAlign = TextAlign.Center,
        style = ElementTheme.typography.fontBodyMdMedium,
        color = ElementTheme.colors.textPrimary,
    )
}

@PreviewsDayNight
@Composable
internal fun DateItemViewPreview(
    @PreviewParameter(MediaItemDateSeparatorProvider::class) date: MediaItem.DateSeparator,
) = ElementPreview {
    DateItemView(date)
}
