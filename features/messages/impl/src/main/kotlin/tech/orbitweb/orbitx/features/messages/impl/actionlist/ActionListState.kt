/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.actionlist

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import tech.orbitweb.orbitx.features.messages.impl.actionlist.model.TimelineItemAction
import tech.orbitweb.orbitx.features.messages.impl.crypto.sendfailure.VerifiedUserSendFailure
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.TimelineItem

data class ActionListState(
    val target: Target,
    val eventSink: (ActionListEvent) -> Unit,
) {
    @Immutable
    sealed interface Target {
        data object None : Target
        data class Loading(val event: TimelineItem.Event) : Target
        data class Success(
            val event: TimelineItem.Event,
            val sentTimeFull: String,
            val displayEmojiReactions: Boolean,
            val recentEmojis: ImmutableList<String>,
            val verifiedUserSendFailure: VerifiedUserSendFailure,
            val actions: ImmutableList<TimelineItemAction>,
        ) : Target
    }
}
