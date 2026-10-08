/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.login.impl.resolver

data class HomeserverData(
    // The computed homeserver url, for which a wellknown file has been retrieved, or just a valid Url
    val homeserverUrl: String,
)
