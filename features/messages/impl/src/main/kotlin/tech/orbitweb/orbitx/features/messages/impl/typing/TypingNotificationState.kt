/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.typing

import kotlinx.collections.immutable.ImmutableList

/**
 * State for the typing notification view.
 */
data class TypingNotificationState(
    /** Whether to render the typing notifications based on the user's preferences. */
    val renderTypingNotifications: Boolean,
    /** The room members currently typing. */
    val typingMembers: ImmutableList<TypingRoomMember>,
    /** Whether to reserve space for the typing notifications at the bottom of the timeline. */
    val reserveSpace: Boolean,
)
