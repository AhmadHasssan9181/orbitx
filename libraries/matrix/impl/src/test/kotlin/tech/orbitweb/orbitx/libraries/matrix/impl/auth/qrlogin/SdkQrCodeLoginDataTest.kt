/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.auth.qrlogin

import com.google.common.truth.Truth.assertThat
import tech.orbitweb.orbitx.libraries.matrix.impl.fixtures.fakes.FakeFfiQrCodeData
import tech.orbitweb.orbitx.libraries.matrix.test.A_HOMESERVER_URL
import org.junit.Test

class SdkQrCodeLoginDataTest {
    @Test
    fun `getServer reads the value from the Rust side, null case`() {
        val sut = SdkQrCodeLoginData(
            rustQrCodeData = FakeFfiQrCodeData(
                serverNameResult = { null },
            ),
        )
        assertThat(sut.serverName()).isNull()
    }

    @Test
    fun `getServer reads the value from the Rust side`() {
        val sut = SdkQrCodeLoginData(
            rustQrCodeData = FakeFfiQrCodeData(
                serverNameResult = { A_HOMESERVER_URL },
            ),
        )
        assertThat(sut.serverName()).isEqualTo(A_HOMESERVER_URL)
    }
}
