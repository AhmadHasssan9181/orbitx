/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.test.auth.qrlogin

import tech.orbitweb.orbitx.libraries.matrix.api.auth.qrlogin.MatrixQrCodeLoginData
import tech.orbitweb.orbitx.libraries.matrix.api.auth.qrlogin.MatrixQrCodeLoginDataFactory
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaRecorder

class FakeMatrixQrCodeLoginDataFactory(
    var parseQrCodeLoginDataResult: () -> Result<MatrixQrCodeLoginData> =
        lambdaRecorder<Result<MatrixQrCodeLoginData>> { Result.success(FakeMatrixQrCodeLoginData()) },
) : MatrixQrCodeLoginDataFactory {
    override fun parseQrCodeData(data: ByteArray): Result<MatrixQrCodeLoginData> {
        return parseQrCodeLoginDataResult()
    }
}

class FakeMatrixQrCodeLoginData(
    private val serverNameResult: () -> String? = { lambdaError() },
) : MatrixQrCodeLoginData {
    override fun serverName() = serverNameResult()
}
