/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.attachments

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize
import tech.orbitweb.orbitx.libraries.mediaviewer.api.local.LocalMedia

@Immutable
sealed interface Attachment : Parcelable {
    @Parcelize
    data class Media(
        val localMedia: LocalMedia,
        // When true, the media was picked through the "Files" picker and should be
        // uploaded without image recompression; videos still use the highest available
        // / best-fit preset rather than an additional size-reduction optimization pass.
        // See https://github.com/element-hq/element-x-android/issues/6365
        val sendAsFile: Boolean = false,
    ) : Attachment
}
