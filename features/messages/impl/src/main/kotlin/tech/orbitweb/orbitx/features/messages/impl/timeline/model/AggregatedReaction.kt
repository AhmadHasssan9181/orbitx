/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.timeline.model

import kotlinx.collections.immutable.ImmutableList
import tech.orbitweb.orbitx.libraries.core.extensions.ellipsize
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId

/**
 * Length at which we ellipsize a reaction key for display
 *
 * Reactions can be free text, so we need to limit the length
 * displayed on screen.
 */
private const val MAX_DISPLAY_CHARS = 16

/**
 * @property currentUserId the ID of the currently logged in user
 * @property key the full reaction key (e.g. "👍", "YES!")
 * @property senders the list of users who sent the reactions
 */
data class AggregatedReaction(
    val currentUserId: UserId,
    val key: String,
    val senders: ImmutableList<AggregatedReactionSender>
) {
    /**
     * The key to be displayed on screen.
     *
     * See [MAX_DISPLAY_CHARS].
     */
    val displayKey: String = key.ellipsize(MAX_DISPLAY_CHARS)

    /**
     * The number of users who reacted with this key.
     */
    val count: Int = senders.count()

    /**
     * True if the reaction has (also) been sent by the current user.
     */
    val isHighlighted: Boolean = senders.any { it.senderId == currentUserId }
}
