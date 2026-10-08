/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.fixtures.factories

import org.matrix.rustcomponents.sdk.UnableToDecryptInfo
import uniffi.matrix_sdk_crypto.UtdCause

internal fun aRustUnableToDecryptInfo(
    eventId: String,
    timeToDecryptMs: ULong? = null,
    cause: UtdCause = UtdCause.UNKNOWN,
    eventLocalAgeMillis: Long = 0L,
    userTrustsOwnIdentity: Boolean = false,
    senderHomeserver: String = "",
    ownHomeserver: String = "",
) = UnableToDecryptInfo(
    eventId = eventId,
    timeToDecryptMs = timeToDecryptMs,
    cause = cause,
    eventLocalAgeMillis = eventLocalAgeMillis,
    userTrustsOwnIdentity = userTrustsOwnIdentity,
    senderHomeserver = senderHomeserver,
    ownHomeserver = ownHomeserver,
)
