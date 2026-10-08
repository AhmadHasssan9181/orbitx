/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.services.analyticsproviders.sentry.di

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import tech.orbitweb.orbitx.libraries.di.identifiers.SentryDsn
import tech.orbitweb.orbitx.libraries.di.identifiers.SentrySdkDsn
import tech.orbitweb.orbitx.services.analyticsproviders.sentry.SentryConfig

@BindingContainer
@ContributesTo(AppScope::class)
object SentryModule {
    @Provides
    fun provideSentryDsn(): SentryDsn? = SentryConfig.DSN.takeIf { it.isNotBlank() }?.let(::SentryDsn)

    @Provides
    fun provideSentrySdkDsn(): SentrySdkDsn? = SentryConfig.SDK_DSN.takeIf { it.isNotBlank() }?.let(::SentrySdkDsn)
}
