/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.mediaupload.api

import android.net.Uri
import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.room.JoinedRoom
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.Timeline

fun interface MediaSenderFactory {
    /**
     * Create a [MediaSender] for the given [Timeline.Mode], in the Room Scope.
     */
    fun create(
        timelineMode: Timeline.Mode,
    ): MediaSender
}

fun interface MediaSenderRoomFactory {
    /**
     * Create a [MediaSender] for the given [JoinedRoom], with timeline mode Live.
     */
    fun create(
        room: JoinedRoom,
    ): MediaSender
}

interface MediaSender {
    suspend fun preProcessMedia(
        uri: Uri,
        mimeType: String,
        mediaOptimizationConfig: MediaOptimizationConfig,
    ): Result<MediaUploadInfo>

    suspend fun sendPreProcessedMedia(
        mediaUploadInfo: MediaUploadInfo,
        caption: String?,
        formattedCaption: String?,
        inReplyToEventId: EventId?,
    ): Result<Unit>

    suspend fun sendMedia(
        uri: Uri,
        mimeType: String,
        caption: String? = null,
        formattedCaption: String? = null,
        inReplyToEventId: EventId? = null,
        mediaOptimizationConfig: MediaOptimizationConfig,
    ): Result<Unit>

    suspend fun sendVoiceMessage(
        uri: Uri,
        mimeType: String,
        waveForm: List<Float>,
        inReplyToEventId: EventId? = null,
        isViewOnce: Boolean = false,
    ): Result<Unit>

    suspend fun sendGallery(
        mediaUploadInfos: List<MediaUploadInfo>,
        caption: String?,
        formattedCaption: String?,
        inReplyToEventId: EventId?,
    ): Result<Unit>

    fun cleanUp()
}
