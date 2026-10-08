/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.ui.common.nodes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.node.node
import tech.orbitweb.orbitx.compound.theme.ElementTheme
import tech.orbitweb.orbitx.libraries.designsystem.preview.ElementPreview
import tech.orbitweb.orbitx.libraries.designsystem.preview.PreviewsDayNight

/**
 * Ref: https://www.figma.com/design/0MMNu7cTOzLOlWb7ctTkv3/Element-X?node-id=1518-85323
 */
fun emptyNode(
    buildContext: BuildContext,
): Node = node(buildContext) { modifier ->
    EmptyView(modifier)
}

@Composable
private fun EmptyView(
    modifier: Modifier = Modifier,
) = Box(
    modifier = modifier
        .fillMaxSize()
        .background(ElementTheme.colors.bgCanvasDefault),
)

@PreviewsDayNight
@Composable
internal fun EmptyViewPreview() = ElementPreview {
    EmptyView(Modifier)
}
