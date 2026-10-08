/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.api.auth

data class MatrixHomeServerDetails(
    val url: String,
    val supportsPasswordLogin: Boolean,
    val supportsOAuthLogin: Boolean,
) {
    val isSupported = supportsPasswordLogin || supportsOAuthLogin
}
