/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.mediaviewer.impl.gallery

import tech.orbitweb.orbitx.libraries.architecture.AsyncData
import tech.orbitweb.orbitx.libraries.designsystem.utils.snackbar.SnackbarMessage
import tech.orbitweb.orbitx.libraries.mediaviewer.impl.R
import tech.orbitweb.orbitx.libraries.mediaviewer.impl.details.MediaBottomSheetState
import tech.orbitweb.orbitx.libraries.mediaviewer.impl.model.GroupedMediaItems

data class MediaGalleryState(
    val roomName: String,
    val mode: MediaGalleryMode,
    val groupedMediaItems: AsyncData<GroupedMediaItems>,
    val mediaBottomSheetState: MediaBottomSheetState,
    val snackbarMessage: SnackbarMessage?,
    val eventSink: (MediaGalleryEvent) -> Unit,
)

enum class MediaGalleryMode(val stringResource: Int) {
    Images(R.string.screen_media_browser_list_mode_media),
    Files(R.string.screen_media_browser_list_mode_files),
}
