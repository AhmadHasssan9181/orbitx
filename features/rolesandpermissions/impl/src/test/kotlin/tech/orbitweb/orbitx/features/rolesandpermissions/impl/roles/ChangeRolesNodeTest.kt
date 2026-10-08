/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.rolesandpermissions.impl.roles

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import tech.orbitweb.orbitx.features.rolesandpermissions.api.ChangeRoomMemberRolesListType
import tech.orbitweb.orbitx.libraries.matrix.api.room.RoomMember

class ChangeRolesNodeTest {
    @Test
    fun `test toRoomMemberRole`() {
        assertThat(ChangeRoomMemberRolesListType.Admins.toRoomMemberRole())
            .isEqualTo(RoomMember.Role.Admin)
        assertThat(ChangeRoomMemberRolesListType.Moderators.toRoomMemberRole())
            .isEqualTo(RoomMember.Role.Moderator)
        assertThat(ChangeRoomMemberRolesListType.SelectNewOwnersWhenLeaving.toRoomMemberRole())
            .isEqualTo(RoomMember.Role.Owner(false))
    }
}
