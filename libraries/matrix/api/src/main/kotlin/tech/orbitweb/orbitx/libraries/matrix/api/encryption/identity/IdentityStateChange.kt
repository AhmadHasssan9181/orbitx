/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.api.encryption.identity

import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId

data class IdentityStateChange(
    val userId: UserId,
    val identityState: IdentityState,
)
