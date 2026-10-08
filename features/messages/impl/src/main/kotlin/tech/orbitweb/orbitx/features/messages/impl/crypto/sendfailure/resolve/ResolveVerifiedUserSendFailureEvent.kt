/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.crypto.sendfailure.resolve

import tech.orbitweb.orbitx.features.messages.impl.timeline.model.TimelineItem

sealed interface ResolveVerifiedUserSendFailureEvent {
    data class ComputeForMessage(
        val messageEvent: TimelineItem.Event,
    ) : ResolveVerifiedUserSendFailureEvent

    data object ResolveAndResend : ResolveVerifiedUserSendFailureEvent
    data object Retry : ResolveVerifiedUserSendFailureEvent
    data object Dismiss : ResolveVerifiedUserSendFailureEvent
}
