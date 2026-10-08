/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.auth.qrlogin

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.matrix.rustcomponents.sdk.QrLoginProgress
import tech.orbitweb.orbitx.libraries.matrix.api.auth.qrlogin.QrCodeLoginStep

class QrLoginProgressExtensionsKtTest {
    @Test
    fun `mapping QrLoginProgress should return expected result`() {
        assertThat(QrLoginProgress.Starting.toStep())
            .isEqualTo(QrCodeLoginStep.Starting)
        assertThat(QrLoginProgress.EstablishingSecureChannel(1u, "01").toStep())
            .isEqualTo(QrCodeLoginStep.EstablishingSecureChannel("01"))
        assertThat(QrLoginProgress.WaitingForToken("userCode").toStep())
            .isEqualTo(QrCodeLoginStep.WaitingForToken("userCode"))
        assertThat(QrLoginProgress.Done.toStep())
            .isEqualTo(QrCodeLoginStep.Finished)
    }
}
