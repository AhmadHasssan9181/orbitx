/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.announcement.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import dev.zacsweers.metro.Inject
import tech.orbitweb.orbitx.features.announcement.api.Announcement
import tech.orbitweb.orbitx.features.announcement.impl.store.AnnouncementStatus
import tech.orbitweb.orbitx.features.announcement.impl.store.AnnouncementStore
import tech.orbitweb.orbitx.libraries.architecture.Presenter
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@Inject
class AnnouncementPresenter(
    private val announcementStore: AnnouncementStore,
) : Presenter<AnnouncementState> {
    @Composable
    override fun present(): AnnouncementState {
        val coroutineScope = rememberCoroutineScope()

        val fullscreenAnnouncementToShow by remember {
            combine(
                flowOf(Unit),
                announcementStore.announcementStatusFlow(Announcement.Fullscreen.Space).map {
                    it == AnnouncementStatus.Show
                },
                // Add other announcements here when needed
            ) { _, showFullscreenSpace ->
                when {
                    showFullscreenSpace -> Announcement.Fullscreen.Space
                    else -> {
                        null
                    }
                }
            }
        }.collectAsState(null)

        fun handle(event: AnnouncementEvent) {
            when (event) {
                is AnnouncementEvent.Continue -> coroutineScope.launch {
                    announcementStore.setAnnouncementStatus(
                        announcement = event.announcement,
                        status = AnnouncementStatus.Shown,
                    )
                }
            }
        }

        return AnnouncementState(
            announcement = fullscreenAnnouncementToShow,
            eventSink = ::handle,
        )
    }
}
