/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.verifysession.impl.incoming

sealed interface IncomingVerificationViewEvents {
    data object GoBack : IncomingVerificationViewEvents
    data object StartVerification : IncomingVerificationViewEvents
    data object IgnoreVerification : IncomingVerificationViewEvents
    data object ConfirmVerification : IncomingVerificationViewEvents
    data object DeclineVerification : IncomingVerificationViewEvents
}
