/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.wellknown.test

import tech.orbitweb.orbitx.libraries.wellknown.api.CustomRecoveryPassphrase
import tech.orbitweb.orbitx.libraries.wellknown.api.ElementWellKnown

fun anElementWellKnown(
    registrationHelperUrl: String? = null,
    enforceElementPro: Boolean? = null,
    rageshakeUrl: String? = null,
    brandColor: String? = null,
    notificationSound: String? = null,
    identityProviderAppScheme: String? = null,
    customRecoveryPassphrase: CustomRecoveryPassphrase? = null,
) = ElementWellKnown(
    registrationHelperUrl = registrationHelperUrl,
    enforceElementPro = enforceElementPro,
    rageshakeUrl = rageshakeUrl,
    brandColor = brandColor,
    notificationSound = notificationSound,
    identityProviderAppScheme = identityProviderAppScheme,
    customRecoveryPassphrase = customRecoveryPassphrase,
)

fun aCustomRecoveryPassphrase(
    minCharacterCount: Int = 8,
) = CustomRecoveryPassphrase(
    minCharacterCount = minCharacterCount,
)
