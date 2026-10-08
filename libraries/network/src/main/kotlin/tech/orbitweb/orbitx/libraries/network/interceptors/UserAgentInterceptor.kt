/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.network.interceptors

import dev.zacsweers.metro.Inject
import tech.orbitweb.orbitx.libraries.network.headers.HttpHeaders
import tech.orbitweb.orbitx.libraries.network.useragent.UserAgentProvider
import okhttp3.Interceptor
import okhttp3.Response

@Inject
class UserAgentInterceptor(
    private val userAgentProvider: UserAgentProvider,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request()
            .newBuilder()
            .header(HttpHeaders.UserAgent, userAgentProvider.provide())
            .build()
        return chain.proceed(newRequest)
    }
}
