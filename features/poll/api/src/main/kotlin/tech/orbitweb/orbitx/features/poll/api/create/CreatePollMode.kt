/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.poll.api.create

import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId

sealed interface CreatePollMode {
    data object NewPoll : CreatePollMode
    data class EditPoll(val eventId: EventId) : CreatePollMode
}
