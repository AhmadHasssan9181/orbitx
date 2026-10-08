/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.network

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import tech.orbitweb.orbitx.libraries.network.interceptors.DynamicHttpLoggingInterceptor
import tech.orbitweb.orbitx.libraries.network.interceptors.FormattedJsonHttpLogger
import tech.orbitweb.orbitx.libraries.network.interceptors.UserAgentInterceptor
import java.util.concurrent.TimeUnit

@BindingContainer
@ContributesTo(AppScope::class)
object NetworkModule {
    @Provides
    @SingleIn(AppScope::class)
    fun providesOkHttpClient(
        userAgentInterceptor: UserAgentInterceptor,
        dynamicHttpLoggingInterceptor: DynamicHttpLoggingInterceptor,
    ): OkHttpClient = OkHttpClient.Builder().apply {
        connectTimeout(30, TimeUnit.SECONDS)
        readTimeout(60, TimeUnit.SECONDS)
        writeTimeout(60, TimeUnit.SECONDS)
        addInterceptor(userAgentInterceptor)
        addInterceptor(dynamicHttpLoggingInterceptor)
    }.build()

    @Provides
    @SingleIn(AppScope::class)
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logger = FormattedJsonHttpLogger(HttpLoggingInterceptor.Level.BODY)
        return HttpLoggingInterceptor(logger)
    }
}
