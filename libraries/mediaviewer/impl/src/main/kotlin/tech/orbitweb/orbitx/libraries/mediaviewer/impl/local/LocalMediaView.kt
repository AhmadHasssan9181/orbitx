/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.mediaviewer.impl.local

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import tech.orbitweb.orbitx.features.viewfolder.api.TextFileViewer
import tech.orbitweb.orbitx.libraries.audio.api.AudioFocus
import tech.orbitweb.orbitx.libraries.core.mimetype.MimeTypes
import tech.orbitweb.orbitx.libraries.core.mimetype.MimeTypes.isMimeTypeAudio
import tech.orbitweb.orbitx.libraries.core.mimetype.MimeTypes.isMimeTypeImage
import tech.orbitweb.orbitx.libraries.core.mimetype.MimeTypes.isMimeTypeVideo
import tech.orbitweb.orbitx.libraries.mediaviewer.api.MediaInfo
import tech.orbitweb.orbitx.libraries.mediaviewer.api.local.LocalMedia
import tech.orbitweb.orbitx.libraries.mediaviewer.impl.local.audio.MediaAudioView
import tech.orbitweb.orbitx.libraries.mediaviewer.impl.local.file.MediaFileView
import tech.orbitweb.orbitx.libraries.mediaviewer.impl.local.image.MediaImageView
import tech.orbitweb.orbitx.libraries.mediaviewer.impl.local.pdf.MediaPdfView
import tech.orbitweb.orbitx.libraries.mediaviewer.impl.local.txt.TextFileView
import tech.orbitweb.orbitx.libraries.mediaviewer.impl.local.video.MediaVideoView

@Composable
fun LocalMediaView(
    localMedia: LocalMedia?,
    bottomPaddingInPixels: Int,
    audioFocus: AudioFocus?,
    onClick: () -> Unit,
    onOpenWith: (() -> Unit)?,
    textFileViewer: TextFileViewer,
    forPreview: Boolean,
    modifier: Modifier = Modifier,
    isDisplayed: Boolean = true,
    isUserSelected: Boolean = false,
    localMediaViewState: LocalMediaViewState = rememberLocalMediaViewState(),
    mediaInfo: MediaInfo? = localMedia?.info,
) {
    val mimeType = mediaInfo?.mimeType
    when {
        mimeType.isMimeTypeImage() -> MediaImageView(
            localMediaViewState = localMediaViewState,
            localMedia = localMedia,
            modifier = modifier,
            forPreview = forPreview,
            onClick = onClick,
        )
        mimeType.isMimeTypeVideo() -> MediaVideoView(
            isDisplayed = isDisplayed,
            localMediaViewState = localMediaViewState,
            bottomPaddingInPixels = bottomPaddingInPixels,
            localMedia = localMedia,
            autoplay = isUserSelected,
            audioFocus = audioFocus,
            forPreview = forPreview,
            modifier = modifier,
        )
        mimeType == MimeTypes.PlainText -> TextFileView(
            localMedia = localMedia,
            textFileViewer = textFileViewer,
            modifier = modifier,
        )
        mimeType == MimeTypes.Pdf -> MediaPdfView(
            localMediaViewState = localMediaViewState,
            localMedia = localMedia,
            modifier = modifier,
            onClick = onClick,
        )
        mimeType.isMimeTypeAudio() -> MediaAudioView(
            isDisplayed = isDisplayed,
            localMediaViewState = localMediaViewState,
            bottomPaddingInPixels = bottomPaddingInPixels,
            localMedia = localMedia,
            info = mediaInfo,
            audioFocus = audioFocus,
            modifier = modifier,
        )
        else -> MediaFileView(
            localMediaViewState = localMediaViewState,
            uri = localMedia?.uri,
            info = mediaInfo,
            modifier = modifier,
            onOpenWith = onOpenWith,
        )
    }
}
