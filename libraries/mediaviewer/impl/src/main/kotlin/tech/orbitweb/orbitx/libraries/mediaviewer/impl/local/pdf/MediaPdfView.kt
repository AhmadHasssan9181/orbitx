/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.mediaviewer.impl.local.pdf

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import tech.orbitweb.orbitx.libraries.mediaviewer.api.local.LocalMedia
import tech.orbitweb.orbitx.libraries.mediaviewer.impl.local.LocalMediaViewState

@Composable
fun MediaPdfView(
    localMediaViewState: LocalMediaViewState,
    localMedia: LocalMedia?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val pdfViewerState = rememberPdfViewerState(
        model = localMedia?.uri,
        zoomableState = localMediaViewState.zoomableState,
    )
    localMediaViewState.isReady = pdfViewerState.isLoaded
    PdfViewer(
        pdfViewerState = pdfViewerState,
        onClick = onClick,
        modifier = modifier,
    )
}
