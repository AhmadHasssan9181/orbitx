/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.timeline.model.event

import kotlinx.collections.immutable.ImmutableList
import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.media.MediaSource
import kotlin.time.Duration

data class TimelineItemVoiceContent(
    val eventId: EventId?,
    override val filename: String,
    override val fileSize: Long?,
    override val caption: String?,
    override val formattedCaption: CharSequence?,
    override val isEdited: Boolean,
    val duration: Duration,
    override val mediaSource: MediaSource,
    override val formattedFileSize: String,
    override val fileExtension: String,
    override val mimeType: String,
    val waveform: ImmutableList<Float>,
) : TimelineItemEventContentWithAttachment {
    override val type: String = "TimelineItemAudioContent"
}
