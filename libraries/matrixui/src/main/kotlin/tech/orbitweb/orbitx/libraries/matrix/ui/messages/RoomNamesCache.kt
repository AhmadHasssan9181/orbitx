/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.ui.messages

import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import tech.orbitweb.orbitx.libraries.di.RoomScope
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomIdOrAlias
import tech.orbitweb.orbitx.libraries.matrix.api.core.toRoomIdOrAlias
import tech.orbitweb.orbitx.libraries.matrix.api.roomlist.RoomSummary
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.runningFold

@SingleIn(RoomScope::class)
@Inject
class RoomNamesCache {
    private val cache = MutableStateFlow(mapOf<RoomIdOrAlias, String?>())
    val updateFlow = cache.drop(1).runningFold(0) { acc, _ -> acc + 1 }

    suspend fun replace(items: List<RoomSummary>) = coroutineScope {
        val roomNamesByRoomIdOrAlias = LinkedHashMap<RoomIdOrAlias, String?>(items.size * 2)
        items
            .forEach { summary ->
                roomNamesByRoomIdOrAlias[summary.info.id.toRoomIdOrAlias()] = summary.info.name
                val canonicalAlias = summary.info.canonicalAlias
                if (canonicalAlias != null) {
                    roomNamesByRoomIdOrAlias[canonicalAlias.toRoomIdOrAlias()] = summary.info.name
                }
            }
        cache.value = roomNamesByRoomIdOrAlias
    }

    fun getDisplayName(roomIdOrAlias: RoomIdOrAlias): String? {
        return cache.value[roomIdOrAlias]
    }
}
