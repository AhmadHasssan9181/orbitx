/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.auth.qrlogin

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import org.matrix.rustcomponents.sdk.QrCodeData
import tech.orbitweb.orbitx.libraries.core.extensions.runCatchingExceptions
import tech.orbitweb.orbitx.libraries.matrix.api.auth.qrlogin.MatrixQrCodeLoginData
import tech.orbitweb.orbitx.libraries.matrix.api.auth.qrlogin.MatrixQrCodeLoginDataFactory

@ContributesBinding(AppScope::class)
class RustQrCodeLoginDataFactory : MatrixQrCodeLoginDataFactory {
    override fun parseQrCodeData(data: ByteArray): Result<MatrixQrCodeLoginData> {
        return runCatchingExceptions { SdkQrCodeLoginData(QrCodeData.fromBytes(data)) }
    }
}
