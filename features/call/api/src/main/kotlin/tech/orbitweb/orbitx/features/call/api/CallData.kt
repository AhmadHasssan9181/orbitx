/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.call.api

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import tech.orbitweb.orbitx.libraries.architecture.NodeInputs
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId

@Parcelize
data class CallData(
    val sessionId: SessionId,
    val roomId: RoomId,
    val isAudioCall: Boolean
) : NodeInputs, Parcelable
