/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.verifysession.impl.outgoing

sealed interface OutgoingVerificationViewEvents {
    data object RequestVerification : OutgoingVerificationViewEvents
    data object StartSasVerification : OutgoingVerificationViewEvents
    data object ConfirmVerification : OutgoingVerificationViewEvents
    data object DeclineVerification : OutgoingVerificationViewEvents
    data object Cancel : OutgoingVerificationViewEvents
    data object Reset : OutgoingVerificationViewEvents
}
