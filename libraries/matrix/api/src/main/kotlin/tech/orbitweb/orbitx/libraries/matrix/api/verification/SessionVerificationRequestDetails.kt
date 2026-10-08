/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.api.verification

import android.os.Parcelable
import tech.orbitweb.orbitx.libraries.matrix.api.core.DeviceId
import tech.orbitweb.orbitx.libraries.matrix.api.core.FlowId
import tech.orbitweb.orbitx.libraries.matrix.api.user.MatrixUser
import kotlinx.parcelize.Parcelize

@Parcelize
data class SessionVerificationRequestDetails(
    val senderProfile: MatrixUser,
    val flowId: FlowId,
    val deviceId: DeviceId,
    val deviceDisplayName: String?,
    val firstSeenTimestamp: Long,
) : Parcelable
