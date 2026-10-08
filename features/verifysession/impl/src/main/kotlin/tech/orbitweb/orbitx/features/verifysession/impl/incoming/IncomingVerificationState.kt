/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.verifysession.impl.incoming

import androidx.compose.runtime.Stable
import tech.orbitweb.orbitx.libraries.matrix.api.core.DeviceId
import tech.orbitweb.orbitx.libraries.matrix.api.verification.SessionVerificationData
import tech.orbitweb.orbitx.libraries.matrix.api.verification.VerificationRequest

data class IncomingVerificationState(
    val step: Step,
    val request: VerificationRequest.Incoming,
    val eventSink: (IncomingVerificationViewEvents) -> Unit,
) {
    @Stable
    sealed interface Step {
        data class Initial(
            val deviceDisplayName: String?,
            val deviceId: DeviceId,
            val formattedSignInTime: String,
            val isWaiting: Boolean,
        ) : Step

        data class Verifying(
            val data: SessionVerificationData,
            val isWaiting: Boolean,
        ) : Step

        data object Canceled : Step
        data object Completed : Step
        data object Failure : Step

        val isTimeLimited: Boolean
            get() = this is Initial ||
                this is Verifying
    }
}
