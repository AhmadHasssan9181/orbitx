/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.fixtures.factories

import org.matrix.rustcomponents.sdk.RoomHero
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.libraries.matrix.test.A_USER_ID

internal fun aRustRoomHero(
    userId: UserId = A_USER_ID,
) = RoomHero(
    userId = userId.value,
    displayName = "displayName",
    avatarUrl = "avatarUrl",
)
