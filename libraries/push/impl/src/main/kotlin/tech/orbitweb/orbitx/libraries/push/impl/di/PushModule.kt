/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.push.impl.di

import android.content.Context
import androidx.core.app.NotificationManagerCompat
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.Binds
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import tech.orbitweb.orbitx.libraries.architecture.Presenter
import tech.orbitweb.orbitx.libraries.di.annotations.ApplicationContext
import tech.orbitweb.orbitx.libraries.push.api.battery.BatteryOptimizationState
import tech.orbitweb.orbitx.libraries.push.impl.battery.BatteryOptimizationPresenter

@BindingContainer
@ContributesTo(AppScope::class)
interface PushModule {
    companion object {
        @Provides
        fun provideNotificationCompatManager(@ApplicationContext context: Context): NotificationManagerCompat {
            return NotificationManagerCompat.from(context)
        }
    }

    @Binds
    fun bindBatteryOptimizationPresenter(presenter: BatteryOptimizationPresenter): Presenter<BatteryOptimizationState>
}
