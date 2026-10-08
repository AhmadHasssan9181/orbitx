/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.startchat.impl.joinbyaddress

sealed interface JoinRoomByAddressEvent {
    data object Dismiss : JoinRoomByAddressEvent
    data object Continue : JoinRoomByAddressEvent
    data class UpdateAddress(val address: String) : JoinRoomByAddressEvent
}
