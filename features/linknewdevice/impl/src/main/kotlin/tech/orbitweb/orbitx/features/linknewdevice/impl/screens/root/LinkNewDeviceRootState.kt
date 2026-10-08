/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.linknewdevice.impl.screens.root

import tech.orbitweb.orbitx.libraries.architecture.AsyncData

data class LinkNewDeviceRootState(
    val isSupported: AsyncData<Boolean>,
    val qrCodeData: AsyncData<Unit>,
    val eventSink: (LinkNewDeviceRootEvent) -> Unit,
)
