/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.mediaviewer.impl.model

import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.mediaviewer.impl.gallery.MediaGalleryMode
import kotlinx.collections.immutable.ImmutableList

data class GroupedMediaItems(
    val imageAndVideoItems: ImmutableList<MediaItem>,
    val fileItems: ImmutableList<MediaItem>,
) {
    fun getItems(mode: MediaGalleryMode): ImmutableList<MediaItem> {
        return when (mode) {
            MediaGalleryMode.Images -> imageAndVideoItems
            MediaGalleryMode.Files -> fileItems
        }
    }
}

fun GroupedMediaItems.hasEvent(eventId: EventId): Boolean {
    return (fileItems + imageAndVideoItems)
        .filterIsInstance<MediaItem.Event>()
        .any { it.eventId() == eventId }
}
