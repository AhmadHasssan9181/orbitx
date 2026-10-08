/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.attachments.preview.error

import tech.orbitweb.orbitx.features.messages.impl.R
import tech.orbitweb.orbitx.libraries.mediaupload.api.MediaPreProcessor

fun sendAttachmentError(
    throwable: Throwable
): Int {
    return if (throwable is MediaPreProcessor.Failure) {
        R.string.screen_media_upload_preview_error_failed_processing
    } else {
        R.string.screen_media_upload_preview_error_failed_sending
    }
}
