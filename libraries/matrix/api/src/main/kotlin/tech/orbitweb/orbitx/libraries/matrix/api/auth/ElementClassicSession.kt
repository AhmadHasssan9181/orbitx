/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.api.auth

import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId

data class ElementClassicSession(
    val userId: UserId,
    val homeserverUrl: String?,
    val secrets: String?,
    val roomKeysVersion: String?,
    val doesContainBackupKey: Boolean,
)
