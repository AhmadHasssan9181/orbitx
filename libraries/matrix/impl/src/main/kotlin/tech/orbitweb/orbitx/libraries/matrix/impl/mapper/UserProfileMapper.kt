/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.mapper

import org.matrix.rustcomponents.sdk.UserProfile
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.libraries.matrix.api.user.MatrixUser

fun UserProfile.map() = MatrixUser(
    userId = UserId(userId),
    displayName = displayName,
    avatarUrl = avatarUrl,
)
