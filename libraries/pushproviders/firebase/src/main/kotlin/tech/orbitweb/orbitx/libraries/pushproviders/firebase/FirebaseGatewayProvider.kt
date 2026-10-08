/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.pushproviders.firebase

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import tech.orbitweb.orbitx.features.enterprise.api.EnterpriseService

interface FirebaseGatewayProvider {
    fun getFirebaseGateway(): String
}

@ContributesBinding(AppScope::class)
class DefaultFirebaseGatewayProvider(
    private val enterpriseService: EnterpriseService,
) : FirebaseGatewayProvider {
    override fun getFirebaseGateway(): String {
        return enterpriseService.firebasePushGateway() ?: FirebaseConfig.PUSHER_HTTP_URL
    }
}
