/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.verifysession.impl.outgoing

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import tech.orbitweb.orbitx.features.verifysession.impl.outgoing.OutgoingVerificationState.Step
import tech.orbitweb.orbitx.features.verifysession.impl.ui.aDecimalsSessionVerificationData
import tech.orbitweb.orbitx.features.verifysession.impl.ui.aEmojisSessionVerificationData
import tech.orbitweb.orbitx.libraries.architecture.AsyncData
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.libraries.matrix.api.verification.VerificationRequest

open class OutgoingVerificationStateProvider : PreviewParameterProvider<OutgoingVerificationState> {
    override val values: Sequence<OutgoingVerificationState>
        get() = sequenceOf(
            anOutgoingVerificationState(
                step = Step.Initial,
                request = anOutgoingSessionVerificationRequest(),
            ),
            anOutgoingVerificationState(
                step = Step.Initial,
                request = anOutgoingUserVerificationRequest(),
            ),
            anOutgoingVerificationState(
                step = Step.AwaitingOtherDeviceResponse,
                request = anOutgoingSessionVerificationRequest(),
            ),
            anOutgoingVerificationState(
                step = Step.AwaitingOtherDeviceResponse,
                request = anOutgoingUserVerificationRequest(),
            ),
            anOutgoingVerificationState(
                step = Step.Verifying(aEmojisSessionVerificationData(), AsyncData.Uninitialized),
                request = anOutgoingSessionVerificationRequest(),
            ),
            anOutgoingVerificationState(
                step = Step.Verifying(aEmojisSessionVerificationData(), AsyncData.Uninitialized),
                request = anOutgoingUserVerificationRequest(),
            ),
            anOutgoingVerificationState(
                step = Step.Verifying(aEmojisSessionVerificationData(), AsyncData.Loading())
            ),
            anOutgoingVerificationState(
                step = Step.Canceled
            ),
            anOutgoingVerificationState(
                step = Step.Ready
            ),
            anOutgoingVerificationState(
                step = Step.Verifying(aDecimalsSessionVerificationData(), AsyncData.Uninitialized)
            ),
            anOutgoingVerificationState(
                step = Step.Completed,
                request = anOutgoingSessionVerificationRequest(),
            ),
            anOutgoingVerificationState(
                step = Step.Completed,
                request = anOutgoingUserVerificationRequest(),
            ),
            anOutgoingVerificationState(
                step = Step.Loading
            ),
            anOutgoingVerificationState(
                step = Step.Exit
            ),
            // Add other state here
        )
}

internal fun anOutgoingUserVerificationRequest() = VerificationRequest.Outgoing.User(userId = UserId("@alice:example.com"))
internal fun anOutgoingSessionVerificationRequest() = VerificationRequest.Outgoing.CurrentSession

internal fun anOutgoingVerificationState(
    step: Step = Step.Initial,
    request: VerificationRequest.Outgoing = anOutgoingSessionVerificationRequest(),
    eventSink: (OutgoingVerificationViewEvents) -> Unit = {},
) = OutgoingVerificationState(
    step = step,
    request = request,
    eventSink = eventSink,
)
