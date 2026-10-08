/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.api.room

import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.core.ThreadId

sealed interface CreateTimelineParams {
    data class Focused(val focusedEventId: EventId) : CreateTimelineParams
    data object MediaOnly : CreateTimelineParams
    data class MediaOnlyFocused(val focusedEventId: EventId) : CreateTimelineParams
    data object PinnedOnly : CreateTimelineParams
    data class Threaded(val threadRootEventId: ThreadId) : CreateTimelineParams
}
