/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.mediaupload.test

import android.net.Uri
import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.mediaupload.api.MediaOptimizationConfig
import tech.orbitweb.orbitx.libraries.mediaupload.api.MediaSender
import tech.orbitweb.orbitx.libraries.mediaupload.api.MediaUploadInfo
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError

class FakeMediaSender(
    private val preProcessMediaResult: () -> Result<MediaUploadInfo> = { lambdaError() },
    private val sendPreProcessedMediaResult: () -> Result<Unit> = { lambdaError() },
    private val sendMediaResult: () -> Result<Unit> = { lambdaError() },
    private val sendVoiceMessageResult: () -> Result<Unit> = { lambdaError() },
    private val sendGalleryResult: () -> Result<Unit> = { lambdaError() },
    private val cleanUpResult: () -> Unit = { lambdaError() },
) : MediaSender {
    override suspend fun preProcessMedia(
        uri: Uri,
        mimeType: String,
        mediaOptimizationConfig: MediaOptimizationConfig,
    ): Result<MediaUploadInfo> {
        return preProcessMediaResult()
    }

    override suspend fun sendPreProcessedMedia(
        mediaUploadInfo: MediaUploadInfo,
        caption: String?,
        formattedCaption: String?,
        inReplyToEventId: EventId?,
    ): Result<Unit> {
        return sendPreProcessedMediaResult()
    }

    override suspend fun sendMedia(
        uri: Uri,
        mimeType: String,
        caption: String?,
        formattedCaption: String?,
        inReplyToEventId: EventId?,
        mediaOptimizationConfig: MediaOptimizationConfig,
    ): Result<Unit> {
        return sendMediaResult()
    }

    override suspend fun sendVoiceMessage(
        uri: Uri,
        mimeType: String,
        waveForm: List<Float>,
        inReplyToEventId: EventId?,
        isViewOnce: Boolean,
    ): Result<Unit> {
        return sendVoiceMessageResult()
    }

    override suspend fun sendGallery(
        mediaUploadInfos: List<MediaUploadInfo>,
        caption: String?,
        formattedCaption: String?,
        inReplyToEventId: EventId?,
    ): Result<Unit> {
        return sendGalleryResult()
    }

    override fun cleanUp() {
        cleanUpResult()
    }
}
