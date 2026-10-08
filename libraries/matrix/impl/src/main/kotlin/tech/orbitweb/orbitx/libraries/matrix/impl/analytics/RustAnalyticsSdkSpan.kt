/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.analytics

import tech.orbitweb.orbitx.libraries.core.extensions.runCatchingExceptions
import tech.orbitweb.orbitx.services.analytics.api.AnalyticsSdkSpan
import kotlinx.coroutines.DelicateCoroutinesApi
import org.matrix.rustcomponents.sdk.LogLevel
import org.matrix.rustcomponents.sdk.Span
import timber.log.Timber

class RustAnalyticsSdkSpan(
    name: String? = null,
    private val parentTraceId: String?,
) : AnalyticsSdkSpan {
    private val inner = if (name != null) {
        Span(
            target = "elementx",
            name = name,
            file = "-",
            line = null,
            level = LogLevel.WARN,
            bridgeTraceId = parentTraceId,
        )
    } else {
        Span.newBridgeSpan(
            target = "elementx",
            parentTraceId = parentTraceId,
        )
    }

    override fun enter() {
        if (Span.current().isNone()) {
            inner.enter()
        } else {
            Timber.w("Not entering span sentry.trace='$parentTraceId' because another span is already active")
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun exit() {
        inner.exit()
        runCatchingExceptions { inner.destroy() }
        Timber.d("Exited span sentry.trace='$parentTraceId'")
    }
}
