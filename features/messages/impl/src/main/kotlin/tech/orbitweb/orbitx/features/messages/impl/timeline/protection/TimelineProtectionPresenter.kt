/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.timeline.protection

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import dev.zacsweers.metro.Inject
import kotlinx.collections.immutable.toImmutableSet
import tech.orbitweb.orbitx.libraries.architecture.Presenter
import tech.orbitweb.orbitx.libraries.core.coroutine.mapState
import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.media.MediaPreviewService
import tech.orbitweb.orbitx.libraries.matrix.api.media.isPreviewEnabled
import tech.orbitweb.orbitx.libraries.matrix.api.room.BaseRoom

@Inject
class TimelineProtectionPresenter(
    private val mediaPreviewService: MediaPreviewService,
    private val room: BaseRoom,
) : Presenter<TimelineProtectionState> {
    private val allowedEvents = mutableStateOf<Set<EventId>>(setOf())

    @Composable
    override fun present(): TimelineProtectionState {
        val mediaPreviewValue = remember {
            mediaPreviewService.mediaPreviewConfigFlow.mapState { config -> config.mediaPreviewValue }
        }.collectAsState()
        val roomInfo = room.roomInfoFlow.collectAsState()
        val protectionState by remember {
            derivedStateOf {
                val isPreviewEnabled = mediaPreviewValue.value.isPreviewEnabled(roomInfo.value.joinRule)
                if (isPreviewEnabled) {
                    ProtectionState.RenderAll
                } else {
                    ProtectionState.RenderOnly(eventIds = allowedEvents.value.toImmutableSet())
                }
            }
        }

        fun handleEvent(event: TimelineProtectionEvent) {
            when (event) {
                is TimelineProtectionEvent.ShowContent -> {
                    allowedEvents.value = allowedEvents.value + setOfNotNull(event.eventId)
                }
            }
        }

        return TimelineProtectionState(
            protectionState = protectionState,
            eventSink = ::handleEvent,
        )
    }
}
