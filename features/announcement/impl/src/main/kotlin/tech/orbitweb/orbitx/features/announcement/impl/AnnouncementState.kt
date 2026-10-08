/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.announcement.impl

import tech.orbitweb.orbitx.features.announcement.api.Announcement

data class AnnouncementState(
    val announcement: Announcement.Fullscreen?,
    val eventSink: (AnnouncementEvent) -> Unit,
)
