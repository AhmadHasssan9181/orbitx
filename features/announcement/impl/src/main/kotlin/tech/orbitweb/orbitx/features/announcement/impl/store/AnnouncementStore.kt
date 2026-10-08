/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.announcement.impl.store

import kotlinx.coroutines.flow.Flow
import tech.orbitweb.orbitx.features.announcement.api.Announcement

interface AnnouncementStore {
    suspend fun setAnnouncementStatus(
        announcement: Announcement,
        status: AnnouncementStatus,
    )

    fun announcementStatusFlow(
        announcement: Announcement,
    ): Flow<AnnouncementStatus>

    suspend fun reset()
}
