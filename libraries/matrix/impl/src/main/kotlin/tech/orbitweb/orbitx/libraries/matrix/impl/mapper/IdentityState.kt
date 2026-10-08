/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.mapper

import tech.orbitweb.orbitx.libraries.matrix.api.encryption.identity.IdentityState
import uniffi.matrix_sdk_crypto.IdentityState as RustIdentityState

fun RustIdentityState.map(): IdentityState = when (this) {
    RustIdentityState.VERIFIED -> IdentityState.Verified
    RustIdentityState.PINNED -> IdentityState.Pinned
    RustIdentityState.PIN_VIOLATION -> IdentityState.PinViolation
    RustIdentityState.VERIFICATION_VIOLATION -> IdentityState.VerificationViolation
}
