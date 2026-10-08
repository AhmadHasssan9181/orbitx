/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.fixtures.factories

import org.matrix.rustcomponents.sdk.UserProfile
import tech.orbitweb.orbitx.libraries.matrix.test.A_USER_ID

internal fun aRustUserProfile(
    userId: String = A_USER_ID.value,
    displayName: String = "displayName",
    avatarUrl: String = "avatarUrl",
) = UserProfile(
    userId = userId,
    displayName = displayName,
    avatarUrl = avatarUrl,
)
