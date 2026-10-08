/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.dateformatter.impl.di

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import tech.orbitweb.orbitx.libraries.dateformatter.impl.TimezoneProvider
import kotlinx.datetime.TimeZone
import java.util.Locale
import kotlin.time.Clock

@BindingContainer
@ContributesTo(AppScope::class)
object DateFormatterModule {
    @Provides
    fun providesClock(): Clock = Clock.System

    @Provides
    fun providesLocale(): Locale = Locale.getDefault()

    @Provides
    fun providesTimezone(): TimezoneProvider = TimezoneProvider { TimeZone.currentSystemDefault() }
}
