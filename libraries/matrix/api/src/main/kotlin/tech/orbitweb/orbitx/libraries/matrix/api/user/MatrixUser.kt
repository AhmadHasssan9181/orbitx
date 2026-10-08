/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.api.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId

@Parcelize
data class MatrixUser(
    val userId: UserId,
    val displayName: String? = null,
    val avatarUrl: String? = null,
) : Parcelable
