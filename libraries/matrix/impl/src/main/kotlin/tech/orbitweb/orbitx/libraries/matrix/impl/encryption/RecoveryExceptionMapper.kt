/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.encryption

import tech.orbitweb.orbitx.libraries.matrix.api.encryption.RecoveryException
import tech.orbitweb.orbitx.libraries.matrix.api.exception.ClientException
import tech.orbitweb.orbitx.libraries.matrix.impl.exception.mapClientException
import org.matrix.rustcomponents.sdk.RecoveryException as RustRecoveryException

fun Throwable.mapRecoveryException(): RecoveryException {
    return when (this) {
        is RustRecoveryException -> {
            when (this) {
                is RustRecoveryException.SecretStorage -> RecoveryException.SecretStorage(
                    message = errorMessage
                )
                is RustRecoveryException.BackupExistsOnServer -> RecoveryException.BackupExistsOnServer
                is RustRecoveryException.Import -> RecoveryException.Import(
                    message = errorMessage
                )
                is RustRecoveryException.Client -> RecoveryException.Client(
                    source.mapClientException()
                )
            }
        }
        else -> RecoveryException.Client(
            ClientException.Other("Unknown error", this)
        )
    }
}
