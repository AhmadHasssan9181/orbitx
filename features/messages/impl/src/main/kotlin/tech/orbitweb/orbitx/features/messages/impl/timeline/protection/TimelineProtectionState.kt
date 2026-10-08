/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.timeline.protection

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableSet
import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId

data class TimelineProtectionState(
    val protectionState: ProtectionState,
    val eventSink: (TimelineProtectionEvent) -> Unit,
) {
    fun hideMediaContent(eventId: EventId?, isMine: Boolean = false) = if (isMine) {
        false
    } else {
        when (protectionState) {
            is ProtectionState.RenderAll -> false
            is ProtectionState.RenderOnly -> eventId !in protectionState.eventIds
        }
    }
}

@Immutable
sealed interface ProtectionState {
    data object RenderAll : ProtectionState
    data class RenderOnly(val eventIds: ImmutableSet<EventId>) : ProtectionState
}
