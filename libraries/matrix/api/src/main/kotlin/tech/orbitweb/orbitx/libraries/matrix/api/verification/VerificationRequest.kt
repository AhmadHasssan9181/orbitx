/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.api.verification

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId

@Immutable
sealed interface VerificationRequest : Parcelable {
    @Immutable
    sealed interface Outgoing : VerificationRequest {
        @Parcelize
        data object CurrentSession : Outgoing

        @Parcelize
        data class User(val userId: UserId) : Outgoing
    }

    sealed class Incoming(open val details: SessionVerificationRequestDetails) : VerificationRequest {
        @Parcelize
        data class OtherSession(override val details: SessionVerificationRequestDetails) : Incoming(details)

        @Parcelize
        data class User(override val details: SessionVerificationRequestDetails) : Incoming(details)
    }
}
