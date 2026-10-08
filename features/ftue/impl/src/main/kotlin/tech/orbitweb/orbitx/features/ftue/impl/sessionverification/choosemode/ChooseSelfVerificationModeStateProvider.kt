/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.ftue.impl.sessionverification.choosemode

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import tech.orbitweb.orbitx.features.logout.api.direct.aDirectLogoutState
import tech.orbitweb.orbitx.libraries.architecture.AsyncData

class ChooseSelfVerificationModeStateProvider :
    PreviewParameterProvider<ChooseSelfVerificationModeState> {
    override val values = sequenceOf(
        aChooseSelfVerificationModeState(
            buttonsState = AsyncData.Success(
                aButtonsState(canUseAnotherDevice = false, canUseRecoveryKey = true),
            ),
        ),
        aChooseSelfVerificationModeState(
            buttonsState = AsyncData.Success(
                aButtonsState(canUseAnotherDevice = false, canUseRecoveryKey = false),
            ),
        ),
        aChooseSelfVerificationModeState(
            buttonsState = AsyncData.Success(
                aButtonsState(canUseAnotherDevice = true, canUseRecoveryKey = true),
            ),
        ),
        aChooseSelfVerificationModeState(
            buttonsState = AsyncData.Success(
                aButtonsState(canUseAnotherDevice = true, canUseRecoveryKey = false),
            ),
        ),
        aChooseSelfVerificationModeState(
            buttonsState = AsyncData.Loading(),
        ),
    )
}

fun aChooseSelfVerificationModeState(
    buttonsState: AsyncData<ChooseSelfVerificationModeState.ButtonsState> = AsyncData.Success(aButtonsState()),
) = ChooseSelfVerificationModeState(
    buttonsState = buttonsState,
    directLogoutState = aDirectLogoutState(),
    eventSink = {},
)

fun aButtonsState(
    canUseAnotherDevice: Boolean = true,
    canUseRecoveryKey: Boolean = true,
) = ChooseSelfVerificationModeState.ButtonsState(
    canUseAnotherDevice = canUseAnotherDevice,
    canUseRecoveryKey = canUseRecoveryKey,
)
