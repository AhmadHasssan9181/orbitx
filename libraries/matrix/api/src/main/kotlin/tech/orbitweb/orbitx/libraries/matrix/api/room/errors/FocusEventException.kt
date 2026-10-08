/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.api.room.errors

import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId

sealed class FocusEventException : Exception() {
    data class InvalidEventId(
        val eventId: String,
        val err: String
    ) : FocusEventException()

    data class EventNotFound(
        val eventId: EventId
    ) : FocusEventException()

    data class Other(
        val msg: String
    ) : FocusEventException()
}
