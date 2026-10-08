/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.api.room.draft

import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId

sealed interface ComposerDraftType {
    data object NewMessage : ComposerDraftType
    data class Reply(val eventId: EventId) : ComposerDraftType
    data class Edit(val eventId: EventId) : ComposerDraftType
}
