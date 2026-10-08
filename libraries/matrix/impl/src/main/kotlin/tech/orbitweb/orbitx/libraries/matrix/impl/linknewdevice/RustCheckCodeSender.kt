/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.linknewdevice

import tech.orbitweb.orbitx.libraries.core.extensions.runCatchingExceptions
import tech.orbitweb.orbitx.libraries.matrix.api.linknewdevice.CheckCodeSender
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.matrix.rustcomponents.sdk.CheckCodeSender as FfiCheckCodeSender

class RustCheckCodeSender(
    private val inner: FfiCheckCodeSender,
    private val sessionDispatcher: CoroutineDispatcher,
) : CheckCodeSender {
    override suspend fun validate(code: UByte): Boolean = withContext(sessionDispatcher) {
        runCatchingExceptions {
            // TODO https://github.com/matrix-org/matrix-rust-sdk/pull/5957
            // inner.validate(code)
            true
        }.getOrNull() ?: true
    }

    override suspend fun send(code: UByte): Result<Unit> = withContext(sessionDispatcher) {
        runCatchingExceptions {
            inner.send(code)
        }
    }
}
