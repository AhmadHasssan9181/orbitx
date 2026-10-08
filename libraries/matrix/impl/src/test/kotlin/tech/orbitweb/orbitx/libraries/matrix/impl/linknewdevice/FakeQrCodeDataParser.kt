/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.linknewdevice

import tech.orbitweb.orbitx.libraries.matrix.impl.fixtures.fakes.FakeFfiQrCodeData
import org.matrix.rustcomponents.sdk.QrCodeData

class FakeQrCodeDataParser : QrCodeDataParser {
    override fun parse(data: ByteArray): QrCodeData {
        return FakeFfiQrCodeData()
    }
}
