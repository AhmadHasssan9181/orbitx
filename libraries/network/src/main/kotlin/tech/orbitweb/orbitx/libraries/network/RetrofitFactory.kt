/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.network

import dev.zacsweers.metro.Inject
import tech.orbitweb.orbitx.libraries.androidutils.json.JsonProvider
import tech.orbitweb.orbitx.libraries.core.uri.ensureTrailingSlash
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

@Inject
class RetrofitFactory(
    private val okHttpClient: () -> OkHttpClient,
    private val json: () -> JsonProvider,
) {
    fun create(baseUrl: String): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl.ensureTrailingSlash())
        .addConverterFactory(json()().asConverterFactory("application/json".toMediaType()))
        .callFactory { request -> okHttpClient().newCall(request) }
        .build()
}
