/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.startchat.impl.root

import tech.orbitweb.orbitx.libraries.matrix.api.user.MatrixUser

sealed interface StartChatEvents {
    data class StartDM(val matrixUser: MatrixUser) : StartChatEvents
    data object CancelStartDM : StartChatEvents
}
