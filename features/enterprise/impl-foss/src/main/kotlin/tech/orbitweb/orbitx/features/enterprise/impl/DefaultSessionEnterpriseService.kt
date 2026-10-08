/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.enterprise.impl

import dev.zacsweers.metro.ContributesBinding
import tech.orbitweb.orbitx.features.enterprise.api.SessionEnterpriseService
import tech.orbitweb.orbitx.libraries.di.SessionScope

@ContributesBinding(SessionScope::class)
class DefaultSessionEnterpriseService : SessionEnterpriseService {
    override suspend fun init() = Unit
    override suspend fun tweakMasUrl(url: String): String = url
    override suspend fun isElementCallAvailable(): Boolean = true
}
