/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.designsystem.components.avatar

import tech.orbitweb.orbitx.libraries.designsystem.preview.USER_NAME_ALICE

fun anAvatarData(
    // Let the id not start with a 'a'.
    id: String = "@id_of_alice:server.org",
    name: String? = USER_NAME_ALICE,
    url: String? = null,
    size: AvatarSize = AvatarSize.RoomListItem,
) = AvatarData(
    id = id,
    name = name,
    url = url,
    size = size,
)
