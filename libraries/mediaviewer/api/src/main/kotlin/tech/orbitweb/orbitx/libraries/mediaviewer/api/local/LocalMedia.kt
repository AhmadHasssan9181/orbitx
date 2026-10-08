/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.mediaviewer.api.local

import android.net.Uri
import android.os.Parcelable
import androidx.compose.runtime.Immutable
import tech.orbitweb.orbitx.libraries.mediaviewer.api.MediaInfo
import kotlinx.parcelize.Parcelize

@Parcelize
@Immutable
data class LocalMedia(
    val uri: Uri,
    val info: MediaInfo,
) : Parcelable
