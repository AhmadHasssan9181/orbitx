/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.ftue.impl.sessionverification.choosemode

import tech.orbitweb.orbitx.features.logout.api.direct.DirectLogoutState
import tech.orbitweb.orbitx.libraries.architecture.AsyncData

data class ChooseSelfVerificationModeState(
    val buttonsState: AsyncData<ButtonsState>,
    val directLogoutState: DirectLogoutState,
    val eventSink: (ChooseSelfVerificationModeEvent) -> Unit,
) {
    data class ButtonsState(
        val canUseAnotherDevice: Boolean,
        val canUseRecoveryKey: Boolean,
    )
}
