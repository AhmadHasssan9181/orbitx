/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.test.media

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import tech.orbitweb.orbitx.libraries.matrix.api.media.MediaPreviewConfig
import tech.orbitweb.orbitx.libraries.matrix.api.media.MediaPreviewService
import tech.orbitweb.orbitx.libraries.matrix.api.media.MediaPreviewValue
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError
import tech.orbitweb.orbitx.tests.testutils.simulateLongTask

class FakeMediaPreviewService(
    override val mediaPreviewConfigFlow: StateFlow<MediaPreviewConfig> = MutableStateFlow(MediaPreviewConfig.DEFAULT),
    private val fetchMediaPreviewConfigResult: () -> Result<MediaPreviewConfig?> = { lambdaError() },
    private val setMediaPreviewValueResult: (MediaPreviewValue) -> Result<Unit> = { lambdaError() },
    private val setHideInviteAvatarsResult: (Boolean) -> Result<Unit> = { lambdaError() },
) : MediaPreviewService {
    override suspend fun fetchMediaPreviewConfig(): Result<MediaPreviewConfig?> = simulateLongTask {
        fetchMediaPreviewConfigResult()
    }

    override suspend fun setMediaPreviewValue(mediaPreviewValue: MediaPreviewValue): Result<Unit> = simulateLongTask {
        setMediaPreviewValueResult(mediaPreviewValue)
    }

    override suspend fun setHideInviteAvatars(hide: Boolean): Result<Unit> = simulateLongTask {
        setHideInviteAvatarsResult(hide)
    }
}
