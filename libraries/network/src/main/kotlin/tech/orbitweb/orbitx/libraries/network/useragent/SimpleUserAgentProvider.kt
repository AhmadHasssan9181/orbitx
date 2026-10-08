/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.network.useragent

class SimpleUserAgentProvider(
    private val userAgent: String = "User agent"
) : UserAgentProvider {
    override fun provide(): String = userAgent
}
