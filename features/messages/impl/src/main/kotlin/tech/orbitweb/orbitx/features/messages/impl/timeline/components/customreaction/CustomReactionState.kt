/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.timeline.components.customreaction

import io.element.android.emojibasebindings.EmojibaseStore
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.TimelineItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet

data class CustomReactionState(
    val target: Target,
    val selectedEmoji: ImmutableSet<String>,
    val recentEmojis: ImmutableList<String>,
    val eventSink: (CustomReactionEvent) -> Unit,
) {
    sealed interface Target {
        data object None : Target
        data class Loading(val event: TimelineItem.Event) : Target
        data class Success(
            val event: TimelineItem.Event,
            val emojibaseStore: EmojibaseStore,
        ) : Target
    }
}
