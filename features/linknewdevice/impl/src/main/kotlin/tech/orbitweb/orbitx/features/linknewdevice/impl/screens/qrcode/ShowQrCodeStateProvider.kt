/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.linknewdevice.impl.screens.qrcode

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import tech.orbitweb.orbitx.libraries.architecture.AsyncData

class ShowQrCodeStateProvider : PreviewParameterProvider<ShowQrCodeState> {
    override val values: Sequence<ShowQrCodeState>
        get() = sequenceOf(
            aShowQrCodeState(),
            aShowQrCodeState(
                data = AsyncData.Loading(),
            ),
        )
}

internal fun aShowQrCodeState(
    data: AsyncData<String> = AsyncData.Success("DATA"),
) = ShowQrCodeState(
    data = data,
)
