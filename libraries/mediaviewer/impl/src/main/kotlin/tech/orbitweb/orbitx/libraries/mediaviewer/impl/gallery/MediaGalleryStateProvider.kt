/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.mediaviewer.impl.gallery

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import kotlinx.collections.immutable.toImmutableList
import tech.orbitweb.orbitx.libraries.architecture.AsyncData
import tech.orbitweb.orbitx.libraries.designsystem.components.media.WaveFormSamples
import tech.orbitweb.orbitx.libraries.designsystem.preview.ROOM_NAME
import tech.orbitweb.orbitx.libraries.matrix.api.core.UniqueId
import tech.orbitweb.orbitx.libraries.mediaviewer.impl.details.MediaBottomSheetState
import tech.orbitweb.orbitx.libraries.mediaviewer.impl.details.aMediaBottomSheetStateDetails
import tech.orbitweb.orbitx.libraries.mediaviewer.impl.model.GroupedMediaItems
import tech.orbitweb.orbitx.libraries.mediaviewer.impl.model.MediaItem
import tech.orbitweb.orbitx.libraries.mediaviewer.impl.model.aMediaItemAudio
import tech.orbitweb.orbitx.libraries.mediaviewer.impl.model.aMediaItemDateSeparator
import tech.orbitweb.orbitx.libraries.mediaviewer.impl.model.aMediaItemFile
import tech.orbitweb.orbitx.libraries.mediaviewer.impl.model.aMediaItemImage
import tech.orbitweb.orbitx.libraries.mediaviewer.impl.model.aMediaItemLoadingIndicator
import tech.orbitweb.orbitx.libraries.mediaviewer.impl.model.aMediaItemVideo
import tech.orbitweb.orbitx.libraries.mediaviewer.impl.model.aMediaItemVoice

open class MediaGalleryStateProvider : PreviewParameterProvider<MediaGalleryState> {
    override val values: Sequence<MediaGalleryState>
        get() = sequenceOf(
            aMediaGalleryState(
                roomName = "A long room name that will be truncated",
            ),
            aMediaGalleryState(groupedMediaItems = AsyncData.Loading()),
            aMediaGalleryState(groupedMediaItems = AsyncData.Success(aGroupedMediaItems())),
            aMediaGalleryState(
                groupedMediaItems = AsyncData.Success(
                    aGroupedMediaItems(
                        imageAndVideoItems = listOf(
                            aMediaItemDateSeparator(id = UniqueId("0")),
                            aMediaItemImage(id = UniqueId("1")),
                            aMediaItemDateSeparator(
                                id = UniqueId("2"),
                                formattedDate = "September 2004",
                            ),
                            aMediaItemImage(id = UniqueId("3")),
                            aMediaItemVideo(id = UniqueId("4")),
                            aMediaItemImage(id = UniqueId("5")),
                            aMediaItemImage(id = UniqueId("6")),
                            aMediaItemImage(id = UniqueId("7")),
                            aMediaItemImage(id = UniqueId("8")),
                            aMediaItemImage(id = UniqueId("9")),
                            aMediaItemLoadingIndicator(),
                        ).toImmutableList()
                    )
                ),
            ),
            aMediaGalleryState(mode = MediaGalleryMode.Files),
            aMediaGalleryState(mode = MediaGalleryMode.Files, groupedMediaItems = AsyncData.Loading()),
            aMediaGalleryState(mode = MediaGalleryMode.Files, groupedMediaItems = AsyncData.Success(aGroupedMediaItems())),
            aMediaGalleryState(
                mode = MediaGalleryMode.Files,
                groupedMediaItems = AsyncData.Success(
                    aGroupedMediaItems(
                        fileItems = listOf(
                            aMediaItemDateSeparator(id = UniqueId("0")),
                            aMediaItemFile(id = UniqueId("1")),
                            aMediaItemDateSeparator(
                                id = UniqueId("2"),
                                formattedDate = "September 2004",
                            ),
                            aMediaItemAudio(id = UniqueId("4")),
                            aMediaItemVoice(
                                id = UniqueId("5"),
                                waveform = WaveFormSamples.realisticWaveForm,
                            ),
                            aMediaItemLoadingIndicator(),
                        ).toImmutableList()
                    )
                ),
            ),
            aMediaGalleryState(mediaBottomSheetState = aMediaBottomSheetStateDetails()),
            aMediaGalleryState(
                groupedMediaItems = AsyncData.Failure(Exception("Failed to load media")),
            ),
            aMediaGalleryState(
                mode = MediaGalleryMode.Files,
                groupedMediaItems = AsyncData.Failure(Exception("Failed to load media")),
            ),
            // Timeline is loaded but does not have relevant content yet for images and videos
            aMediaGalleryState(
                groupedMediaItems = AsyncData.Success(
                    aGroupedMediaItems(
                        imageAndVideoItems = listOf(
                            aMediaItemLoadingIndicator(),
                        ),
                    )
                )
            ),
            // Timeline is loaded but does not have relevant content yet for files
            aMediaGalleryState(
                mode = MediaGalleryMode.Files,
                groupedMediaItems = AsyncData.Success(
                    aGroupedMediaItems(
                        fileItems = listOf(
                            aMediaItemLoadingIndicator(),
                        ),
                    )
                )
            ),
        )
}

private fun aMediaGalleryState(
    roomName: String = ROOM_NAME,
    mode: MediaGalleryMode = MediaGalleryMode.Images,
    groupedMediaItems: AsyncData<GroupedMediaItems> = AsyncData.Uninitialized,
    mediaBottomSheetState: MediaBottomSheetState = MediaBottomSheetState.Hidden,
) = MediaGalleryState(
    roomName = roomName,
    mode = mode,
    groupedMediaItems = groupedMediaItems,
    mediaBottomSheetState = mediaBottomSheetState,
    snackbarMessage = null,
    eventSink = {}
)

fun aGroupedMediaItems(
    imageAndVideoItems: List<MediaItem> = emptyList(),
    fileItems: List<MediaItem> = emptyList(),
) = GroupedMediaItems(
    imageAndVideoItems = imageAndVideoItems.toImmutableList(),
    fileItems = fileItems.toImmutableList(),
)
