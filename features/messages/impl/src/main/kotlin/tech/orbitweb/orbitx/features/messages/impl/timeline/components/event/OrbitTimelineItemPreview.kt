/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.timeline.components.event

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import tech.orbitweb.orbitx.compound.theme.ElementTheme
import tech.orbitweb.orbitx.libraries.designsystem.preview.ElementPreview
import tech.orbitweb.orbitx.libraries.designsystem.theme.messageFromMeBackground

@Composable
internal fun OrbitTimelineItemPreview(
    content: @Composable BoxScope.() -> Unit,
) = ElementPreview {
    Box(
        modifier = Modifier
            .background(ElementTheme.colors.messageFromMeBackground)
            .padding(4.dp),
        content = content,
    )
}

@Composable
internal fun ElementTimelineItemPreview(
    content: @Composable BoxScope.() -> Unit,
) = OrbitTimelineItemPreview(content = content)
