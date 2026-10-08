/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.call.api

import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId

/**
 * Value for the local current call.
 */
sealed interface CurrentCall {
    data object None : CurrentCall

    data class RoomCall(
        val roomId: RoomId,
    ) : CurrentCall

    data class ExternalUrl(
        val url: String,
    ) : CurrentCall
}
