/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.viewfolder.impl

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import kotlinx.collections.immutable.ImmutableList
import tech.orbitweb.orbitx.features.viewfolder.api.TextFileViewer
import tech.orbitweb.orbitx.features.viewfolder.impl.file.ColorationMode
import tech.orbitweb.orbitx.features.viewfolder.impl.file.FileContent

@ContributesBinding(AppScope::class)
class DefaultTextFileViewer : TextFileViewer {
    @Composable
    override fun Render(
        lines: ImmutableList<String>,
        modifier: Modifier
    ) {
        FileContent(
            lines = lines,
            colorationMode = ColorationMode.None,
            modifier = modifier
        )
    }
}
