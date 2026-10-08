/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.messagecomposer

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import tech.orbitweb.orbitx.features.messages.impl.messagecomposer.suggestions.RoomAliasSuggestion
import tech.orbitweb.orbitx.features.messages.impl.messagecomposer.suggestions.RoomAliasSuggestionsDataSource

class FakeRoomAliasSuggestionsDataSource(
    initialData: List<RoomAliasSuggestion> = emptyList()
) : RoomAliasSuggestionsDataSource {
    private val roomAliasSuggestions = MutableStateFlow(initialData)

    override fun getAllRoomAliasSuggestions(): Flow<List<RoomAliasSuggestion>> {
        return roomAliasSuggestions
    }

    fun emitRoomAliasSuggestions(newData: List<RoomAliasSuggestion>) {
        roomAliasSuggestions.value = newData
    }
}
