/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.crypto.sendfailure.resolve

import tech.orbitweb.orbitx.features.messages.impl.crypto.sendfailure.VerifiedUserSendFailure
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction

data class ResolveVerifiedUserSendFailureState(
    val verifiedUserSendFailure: VerifiedUserSendFailure,
    val resolveAction: AsyncAction<Unit>,
    val retryAction: AsyncAction<Unit>,
    val eventSink: (ResolveVerifiedUserSendFailureEvent) -> Unit
)
