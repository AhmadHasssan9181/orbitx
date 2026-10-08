/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event

import kotlinx.collections.immutable.ImmutableList

data class EventReaction(
    val key: String,
    val senders: ImmutableList<ReactionSender>
)
