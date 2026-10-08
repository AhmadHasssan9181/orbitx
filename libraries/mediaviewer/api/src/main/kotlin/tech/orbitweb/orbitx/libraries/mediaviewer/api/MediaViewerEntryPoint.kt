/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.mediaviewer.api

import android.os.Parcelable
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.plugin.Plugin
import tech.orbitweb.orbitx.libraries.architecture.FeatureEntryPoint
import tech.orbitweb.orbitx.libraries.architecture.NodeInputs
import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.media.MediaSource
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.Timeline
import kotlinx.parcelize.Parcelize

interface MediaViewerEntryPoint : FeatureEntryPoint {
    fun createNode(
        parentNode: Node,
        buildContext: BuildContext,
        params: Params,
        callback: Callback,
    ): Node

    fun createParamsForAvatar(filename: String, avatarUrl: String): Params

    interface Callback : Plugin {
        fun onDone()
        fun viewInTimeline(eventId: EventId)
        fun forwardEvent(eventId: EventId, fromPinnedEvents: Boolean)
    }

    sealed interface Params : NodeInputs {
        data class RoomMedia(
            val mode: MediaViewerMode,
            val eventId: EventId?,
            val mediaInfo: MediaInfo,
            val mediaSource: MediaSource,
            val thumbnailSource: MediaSource?,
        ) : Params

        data class EventGallery(
            val eventId: EventId?,
            val galleryInfo: GalleryInfo,
            val galleryItems: List<GalleryItemData>,
            val fromPinnedMessages: Boolean,
        ) : Params

        data class Avatar(
            val avatarInfo: AvatarInfo,
            val mediaSource: MediaSource,
            val thumbnailSource: MediaSource?,
        ) : Params
    }

    sealed interface MediaViewerMode : Parcelable {
        @Parcelize
        data class EventGallery(val fromPinnedMessages: Boolean) : MediaViewerMode

        @Parcelize
        data class TimelineImagesAndVideos(val timelineMode: Timeline.Mode) : MediaViewerMode

        @Parcelize
        data class TimelineFilesAndAudios(val timelineMode: Timeline.Mode) : MediaViewerMode
    }
}
