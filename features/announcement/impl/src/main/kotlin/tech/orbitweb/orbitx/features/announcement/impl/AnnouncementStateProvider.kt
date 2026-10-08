/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.announcement.impl

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import tech.orbitweb.orbitx.features.announcement.api.Announcement

open class AnnouncementStateProvider : PreviewParameterProvider<AnnouncementState> {
    override val values: Sequence<AnnouncementState>
        get() = sequenceOf(
            anAnnouncementState(),
            anAnnouncementState(
                announcement = Announcement.Fullscreen.Space,
            ),
        )
}

fun anAnnouncementState(
    announcement: Announcement.Fullscreen? = null,
    eventSink: (AnnouncementEvent) -> Unit = {},
) = AnnouncementState(
    announcement = announcement,
    eventSink = eventSink,
)
