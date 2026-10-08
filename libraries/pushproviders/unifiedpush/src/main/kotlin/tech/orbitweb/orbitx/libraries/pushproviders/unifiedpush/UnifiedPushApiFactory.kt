/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.pushproviders.unifiedpush

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import tech.orbitweb.orbitx.libraries.network.RetrofitFactory
import tech.orbitweb.orbitx.libraries.pushproviders.unifiedpush.network.UnifiedPushApi

interface UnifiedPushApiFactory {
    fun create(baseUrl: String): UnifiedPushApi
}

@ContributesBinding(AppScope::class)
class DefaultUnifiedPushApiFactory(
    private val retrofitFactory: RetrofitFactory,
) : UnifiedPushApiFactory {
    override fun create(baseUrl: String): UnifiedPushApi {
        return retrofitFactory.create(baseUrl)
            .create(UnifiedPushApi::class.java)
    }
}
