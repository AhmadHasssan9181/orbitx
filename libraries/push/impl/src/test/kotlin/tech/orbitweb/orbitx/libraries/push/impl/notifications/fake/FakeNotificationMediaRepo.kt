/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.push.impl.notifications.fake

import tech.orbitweb.orbitx.libraries.matrix.api.media.MediaSource
import tech.orbitweb.orbitx.libraries.push.impl.notifications.NotificationMediaRepo
import java.io.File

class FakeNotificationMediaRepo : NotificationMediaRepo {
    override suspend fun getMediaFile(
        mediaSource: MediaSource,
        mimeType: String?,
        filename: String?,
    ): Result<File> {
        return Result.failure(IllegalStateException("Fake class"))
    }
}
