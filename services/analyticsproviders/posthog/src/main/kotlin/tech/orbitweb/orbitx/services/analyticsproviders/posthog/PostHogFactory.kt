/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.services.analyticsproviders.posthog

import android.content.Context
import com.posthog.PostHogInterface
import com.posthog.android.PostHogAndroid
import com.posthog.android.PostHogAndroidConfig
import dev.zacsweers.metro.Inject
import tech.orbitweb.orbitx.libraries.core.meta.BuildMeta
import tech.orbitweb.orbitx.libraries.di.annotations.ApplicationContext

@Inject
class PostHogFactory(
    @ApplicationContext private val context: Context,
    private val buildMeta: BuildMeta,
    private val posthogEndpointConfigProvider: PosthogEndpointConfigProvider,
) {
    fun createPosthog(): PostHogInterface? {
        val endpoint = posthogEndpointConfigProvider.provide() ?: return null
        return PostHogAndroid.with(
            context,
            PostHogAndroidConfig(
                apiKey = endpoint.apiKey,
                host = endpoint.host,
                captureApplicationLifecycleEvents = false,
                captureDeepLinks = false,
                captureScreenViews = false,
            ).also {
                it.debug = buildMeta.isDebuggable
                it.sendFeatureFlagEvent = false
            }
        )
    }
}
