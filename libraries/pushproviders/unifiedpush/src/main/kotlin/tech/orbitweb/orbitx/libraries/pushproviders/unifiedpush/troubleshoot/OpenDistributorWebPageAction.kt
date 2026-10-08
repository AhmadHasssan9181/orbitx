/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.pushproviders.unifiedpush.troubleshoot

import android.content.Context
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import tech.orbitweb.orbitx.libraries.androidutils.system.openUrlInExternalApp
import tech.orbitweb.orbitx.libraries.di.annotations.ApplicationContext
import tech.orbitweb.orbitx.libraries.pushproviders.unifiedpush.UnifiedPushConfig

interface OpenDistributorWebPageAction {
    fun execute()
}

@ContributesBinding(AppScope::class)
class DefaultOpenDistributorWebPageAction(
    @ApplicationContext private val context: Context,
) : OpenDistributorWebPageAction {
    override fun execute() {
        // Open the distributor download page
        context.openUrlInExternalApp(
            url = UnifiedPushConfig.UNIFIED_PUSH_DISTRIBUTORS_URL,
        )
    }
}
