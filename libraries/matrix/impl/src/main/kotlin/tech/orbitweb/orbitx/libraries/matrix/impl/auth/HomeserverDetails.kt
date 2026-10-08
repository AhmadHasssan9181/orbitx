/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.auth

import tech.orbitweb.orbitx.libraries.matrix.api.auth.MatrixHomeServerDetails
import org.matrix.rustcomponents.sdk.HomeserverLoginDetails

fun HomeserverLoginDetails.map(): MatrixHomeServerDetails = use {
    MatrixHomeServerDetails(
        url = url(),
        supportsPasswordLogin = supportsPasswordLogin(),
        supportsOAuthLogin = supportsOauthLogin(),
    )
}
