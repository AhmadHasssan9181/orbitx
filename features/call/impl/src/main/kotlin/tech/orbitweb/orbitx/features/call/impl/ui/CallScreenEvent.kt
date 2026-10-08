/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.call.impl.ui

import tech.orbitweb.orbitx.features.call.impl.utils.WidgetMessageInterceptor

sealed interface CallScreenEvent {
    data object Hangup : CallScreenEvent
    data class SetupMessageChannels(val widgetMessageInterceptor: WidgetMessageInterceptor) : CallScreenEvent
    data class OnWebViewError(val description: String?) : CallScreenEvent
}
