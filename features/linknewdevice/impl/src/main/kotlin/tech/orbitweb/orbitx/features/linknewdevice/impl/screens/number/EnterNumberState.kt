/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.linknewdevice.impl.screens.number

import tech.orbitweb.orbitx.features.linknewdevice.impl.screens.number.model.Number
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction

data class EnterNumberState(
    val number: String,
    val sendingCode: AsyncAction<Unit>,
    val eventSink: (EnterNumberEvent) -> Unit,
) {
    val numberEntry = Number.createEmpty(Config.VERIFICATION_CODE_LENGTH).fillWith(number)
    val isContinueButtonEnabled: Boolean
        get() = numberEntry.isComplete() && !sendingCode.isLoading()
}
