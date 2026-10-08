/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.test.auth

import tech.orbitweb.orbitx.libraries.matrix.api.auth.MatrixHomeServerDetails
import tech.orbitweb.orbitx.libraries.matrix.test.A_HOMESERVER_URL

fun aMatrixHomeServerDetails(
    url: String = A_HOMESERVER_URL,
    supportsPasswordLogin: Boolean = false,
    supportsOAuthLogin: Boolean = false,
) = MatrixHomeServerDetails(
    url = url,
    supportsPasswordLogin = supportsPasswordLogin,
    supportsOAuthLogin = supportsOAuthLogin,
)
