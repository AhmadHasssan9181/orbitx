/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.pushproviders.unifiedpush

import android.content.Context
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import org.unifiedpush.android.connector.UnifiedPush
import tech.orbitweb.orbitx.libraries.androidutils.system.getApplicationLabel
import tech.orbitweb.orbitx.libraries.di.annotations.ApplicationContext
import tech.orbitweb.orbitx.libraries.pushproviders.api.Distributor

interface UnifiedPushDistributorProvider {
    fun getDistributors(): List<Distributor>
}

@ContributesBinding(AppScope::class)
class DefaultUnifiedPushDistributorProvider(
    @ApplicationContext private val context: Context,
) : UnifiedPushDistributorProvider {
    override fun getDistributors(): List<Distributor> {
        val distributors = UnifiedPush.getDistributors(context)
        return distributors.mapNotNull {
            if (it == context.packageName) {
                // Exclude self
                null
            } else {
                Distributor(it, context.getApplicationLabel(it))
            }
        }
    }
}
