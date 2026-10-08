/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.fixtures

import tech.orbitweb.orbitx.features.messages.impl.attachments.Attachment
import tech.orbitweb.orbitx.libraries.mediaviewer.api.local.LocalMedia

fun aMediaAttachment(localMedia: LocalMedia, sendAsFile: Boolean = false) = Attachment.Media(
    localMedia = localMedia,
    sendAsFile = sendAsFile,
)
