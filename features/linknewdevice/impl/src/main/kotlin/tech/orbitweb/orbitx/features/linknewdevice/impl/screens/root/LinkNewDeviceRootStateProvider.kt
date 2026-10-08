/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.linknewdevice.impl.screens.root

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import tech.orbitweb.orbitx.libraries.architecture.AsyncData
import tech.orbitweb.orbitx.libraries.matrix.api.linknewdevice.ErrorType

open class LinkNewDeviceRootStateProvider : PreviewParameterProvider<LinkNewDeviceRootState> {
    override val values: Sequence<LinkNewDeviceRootState>
        get() = sequenceOf(
            aLinkNewDeviceRootState(),
            aLinkNewDeviceRootState(isSupported = AsyncData.Success(true)),
            aLinkNewDeviceRootState(isSupported = AsyncData.Success(false)),
            aLinkNewDeviceRootState(isSupported = AsyncData.Failure(Exception("Should not happen"))),
            aLinkNewDeviceRootState(
                isSupported = AsyncData.Success(true),
                qrCodeData = AsyncData.Loading(),
            ),
            aLinkNewDeviceRootState(
                isSupported = AsyncData.Success(true),
                qrCodeData = AsyncData.Failure(ErrorType.NotFound("The rendezvous session was not found and might have expired")),
            ),
        )
}

fun aLinkNewDeviceRootState(
    isSupported: AsyncData<Boolean> = AsyncData.Uninitialized,
    qrCodeData: AsyncData<Unit> = AsyncData.Uninitialized,
    eventSink: (LinkNewDeviceRootEvent) -> Unit = { },
) = LinkNewDeviceRootState(
    isSupported = isSupported,
    qrCodeData = qrCodeData,
    eventSink = eventSink,
)
