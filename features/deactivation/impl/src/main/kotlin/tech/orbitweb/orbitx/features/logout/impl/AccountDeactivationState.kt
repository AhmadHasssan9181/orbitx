/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.logout.impl

import android.os.Parcelable
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import kotlinx.parcelize.Parcelize

data class AccountDeactivationState(
    val deactivateFormState: DeactivateFormState,
    val accountDeactivationAction: AsyncAction<Unit>,
    val eventSink: (AccountDeactivationEvents) -> Unit,
) {
    val submitEnabled: Boolean
        get() = accountDeactivationAction is AsyncAction.Uninitialized &&
            deactivateFormState.password.isNotEmpty()
}

@Parcelize
data class DeactivateFormState(
    val eraseData: Boolean,
    val password: String
) : Parcelable {
    companion object {
        val Default = DeactivateFormState(false, "")
    }
}
