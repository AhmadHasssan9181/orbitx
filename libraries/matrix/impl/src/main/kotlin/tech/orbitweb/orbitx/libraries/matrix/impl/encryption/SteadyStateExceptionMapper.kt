/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.encryption

import tech.orbitweb.orbitx.libraries.matrix.api.encryption.SteadyStateException
import org.matrix.rustcomponents.sdk.SteadyStateException as RustSteadyStateException

class SteadyStateExceptionMapper {
    fun map(data: RustSteadyStateException): SteadyStateException {
        return when (data) {
            is RustSteadyStateException.BackupDisabled -> SteadyStateException.BackupDisabled(
                message = data.message.orEmpty()
            )
            is RustSteadyStateException.Connection -> SteadyStateException.Connection(
                message = data.message.orEmpty()
            )
            is RustSteadyStateException.Lagged -> SteadyStateException.Lagged(
                message = data.message.orEmpty()
            )
        }
    }
}
