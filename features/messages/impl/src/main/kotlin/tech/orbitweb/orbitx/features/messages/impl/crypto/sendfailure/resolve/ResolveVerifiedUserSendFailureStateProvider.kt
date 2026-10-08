/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.crypto.sendfailure.resolve

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import tech.orbitweb.orbitx.features.messages.impl.crypto.sendfailure.VerifiedUserSendFailure
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.designsystem.preview.USER_NAME_ALICE

open class ResolveVerifiedUserSendFailureStateProvider : PreviewParameterProvider<ResolveVerifiedUserSendFailureState> {
    override val values: Sequence<ResolveVerifiedUserSendFailureState>
        get() = sequenceOf(
            aResolveVerifiedUserSendFailureState(),
            aResolveVerifiedUserSendFailureState(
                verifiedUserSendFailure = anUnsignedDeviceSendFailure()
            ),
            aResolveVerifiedUserSendFailureState(
                verifiedUserSendFailure = aChangedIdentitySendFailure()
            )
        )
}

fun aResolveVerifiedUserSendFailureState(
    verifiedUserSendFailure: VerifiedUserSendFailure = VerifiedUserSendFailure.None,
    resolveAction: AsyncAction<Unit> = AsyncAction.Uninitialized,
    retryAction: AsyncAction<Unit> = AsyncAction.Uninitialized,
    eventSink: (ResolveVerifiedUserSendFailureEvent) -> Unit = {}
) = ResolveVerifiedUserSendFailureState(
    verifiedUserSendFailure = verifiedUserSendFailure,
    resolveAction = resolveAction,
    retryAction = retryAction,
    eventSink = eventSink
)

fun anUnsignedDeviceSendFailure(userDisplayName: String = USER_NAME_ALICE) = VerifiedUserSendFailure.UnsignedDevice.FromOther(
    userDisplayName = userDisplayName,
)

fun aChangedIdentitySendFailure(userDisplayName: String = USER_NAME_ALICE) = VerifiedUserSendFailure.ChangedIdentity(
    userDisplayName = userDisplayName,
)
