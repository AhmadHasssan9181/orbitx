/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.space.impl.leave

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.architecture.AsyncData

data class LeaveSpaceState(
    val spaceName: String?,
    val needsOwnerChange: Boolean,
    val areCreatorsPrivileged: Boolean,
    val selectableSpaceRooms: AsyncData<ImmutableList<SelectableSpaceRoom>>,
    val leaveSpaceAction: AsyncAction<Unit>,
    val eventSink: (LeaveSpaceEvents) -> Unit,
) {
    private val rooms = selectableSpaceRooms.dataOrNull().orEmpty().toImmutableList()
    private val lastAdminRooms: ImmutableList<SelectableSpaceRoom>
    private val selectableRooms: ImmutableList<SelectableSpaceRoom>

    init {
        val partition = rooms.partition { it.isLastOwner && it.joinedMembersCount > 1 }
        lastAdminRooms = partition.first.toImmutableList()
        selectableRooms = partition.second.toImmutableList()
    }

    /**
     * True if we should show the quick action to select/deselect all rooms.
     */
    val showQuickAction = needsOwnerChange.not() && selectableRooms.isNotEmpty()

    /**
     * True if we should show the leave button.
     */
    val showLeaveButton = needsOwnerChange.not() && selectableSpaceRooms is AsyncData.Success

    /**
     * True if there all the selectable rooms are selected.
     */
    val areAllSelected = selectableRooms.all { it.isSelected }

    /**
     * True if there are rooms but the user is the last admin in all of them.
     */
    val hasOnlyLastAdminRoom = lastAdminRooms.isNotEmpty() && selectableRooms.isEmpty()

    /**
     * Number of selected rooms.
     */
    val selectedRoomsCount = selectableRooms.count { it.isSelected }
}
