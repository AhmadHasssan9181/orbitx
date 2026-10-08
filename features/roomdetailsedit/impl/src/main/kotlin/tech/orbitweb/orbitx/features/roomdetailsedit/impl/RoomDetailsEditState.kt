/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.roomdetailsedit.impl

import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.ui.media.AvatarAction
import tech.orbitweb.orbitx.libraries.permissions.api.PermissionsState
import kotlinx.collections.immutable.ImmutableList

data class RoomDetailsEditState(
    val roomId: RoomId,
    /** The raw room name (i.e. the room name from the state event `m.room.name`), not the display name. */
    val roomRawName: String,
    val canChangeName: Boolean,
    val roomTopic: String,
    val canChangeTopic: Boolean,
    val roomAvatarUrl: String?,
    val canChangeAvatar: Boolean,
    val avatarActions: ImmutableList<AvatarAction>,
    val saveButtonEnabled: Boolean,
    val saveAction: AsyncAction<Unit>,
    val cameraPermissionState: PermissionsState,
    val isSpace: Boolean,
    val eventSink: (RoomDetailsEditEvent) -> Unit
)
