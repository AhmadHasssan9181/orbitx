/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.location.api

import android.os.Parcelable
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.libraries.matrix.api.room.location.AssetType
import kotlinx.parcelize.Parcelize

sealed interface ShowLocationMode : Parcelable {
    @Parcelize
    data class Static(
        val location: Location,
        val senderName: String,
        val senderId: UserId,
        val senderAvatarUrl: String?,
        val timestamp: Long,
        val assetType: AssetType?,
    ) : ShowLocationMode

    @Parcelize
    data class Live(
        val senderId: UserId
    ) : ShowLocationMode
}
