/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.login.impl.screens.qrcode.scan

sealed interface QrCodeScanEvents {
    data class QrCodeScanned(val code: ByteArray) : QrCodeScanEvents
    data object TryAgain : QrCodeScanEvents
}
