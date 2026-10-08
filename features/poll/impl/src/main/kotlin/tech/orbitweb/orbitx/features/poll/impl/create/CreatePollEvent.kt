/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.poll.impl.create

import tech.orbitweb.orbitx.libraries.matrix.api.poll.PollKind

sealed interface CreatePollEvent {
    data object Save : CreatePollEvent
    data class Delete(val confirmed: Boolean) : CreatePollEvent
    data class SetQuestion(val question: String) : CreatePollEvent
    data class SetAnswer(val index: Int, val text: String) : CreatePollEvent
    data object AddAnswer : CreatePollEvent
    data class RemoveAnswer(val index: Int) : CreatePollEvent
    data class SetPollKind(val pollKind: PollKind) : CreatePollEvent
    data object NavBack : CreatePollEvent
    data object ConfirmNavBack : CreatePollEvent
    data object HideConfirmation : CreatePollEvent
}
