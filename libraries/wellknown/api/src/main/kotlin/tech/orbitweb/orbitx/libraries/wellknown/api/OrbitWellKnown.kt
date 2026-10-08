/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.wellknown.api

data class OrbitWellKnown(
    val registrationHelperUrl: String?,
    val enforceElementPro: Boolean?,
    val rageshakeUrl: String?,
    val brandColor: String?,
    val notificationSound: String?,
    val identityProviderAppScheme: String?,
    val customRecoveryPassphrase: CustomRecoveryPassphrase?,
)

typealias ElementWellKnown = OrbitWellKnown

