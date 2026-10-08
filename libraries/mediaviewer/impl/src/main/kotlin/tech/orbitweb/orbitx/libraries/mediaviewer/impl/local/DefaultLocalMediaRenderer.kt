/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.mediaviewer.impl.local

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import tech.orbitweb.orbitx.features.viewfolder.api.TextFileViewer
import tech.orbitweb.orbitx.libraries.audio.api.AudioFocus
import tech.orbitweb.orbitx.libraries.mediaviewer.api.local.LocalMedia
import tech.orbitweb.orbitx.libraries.mediaviewer.api.local.LocalMediaRenderer
import me.saket.telephoto.zoomable.OverzoomEffect
import me.saket.telephoto.zoomable.ZoomSpec
import me.saket.telephoto.zoomable.rememberZoomableState

@ContributesBinding(AppScope::class)
class DefaultLocalMediaRenderer(
    private val textFileViewer: TextFileViewer,
    private val audioFocus: AudioFocus,
) : LocalMediaRenderer {
    @Composable
    override fun Render(localMedia: LocalMedia) {
        val localMediaViewState = rememberLocalMediaViewState(
            zoomableState = rememberZoomableState(
                zoomSpec = ZoomSpec(maxZoomFactor = 4f, overzoomEffect = OverzoomEffect.NoLimits)
            )
        )
        LocalMediaView(
            modifier = Modifier.fillMaxSize(),
            bottomPaddingInPixels = 0,
            localMedia = localMedia,
            localMediaViewState = localMediaViewState,
            textFileViewer = textFileViewer,
            audioFocus = audioFocus,
            forPreview = true,
            onClick = {},
            onOpenWith = null,
        )
    }
}
