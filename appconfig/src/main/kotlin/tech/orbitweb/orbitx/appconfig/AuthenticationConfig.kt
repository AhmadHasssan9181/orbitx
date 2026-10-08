/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.appconfig

object AuthenticationConfig {
    const val MATRIX_ORG_URL = "https://matrix.org"

    /**
     * URL with some docs that explain what's sliding sync and how to add it to your home server.
     */
    const val SLIDING_SYNC_READ_MORE_URL = "https://github.com/matrix-org/sliding-sync/blob/main/docs/Landing.md"

    /**
     * Force a sliding sync proxy url, if not null, the proxy url in the .well-known file will be ignored.
     */
    val SLIDING_SYNC_PROXY_URL: String? = null
}
