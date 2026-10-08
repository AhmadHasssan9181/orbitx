/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.call.impl.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import tech.orbitweb.orbitx.libraries.architecture.AsyncData

open class CallScreenStateProvider : PreviewParameterProvider<CallScreenState> {
    override val values: Sequence<CallScreenState>
        get() = sequenceOf(
            aCallScreenState(),
            aCallScreenState(urlState = AsyncData.Loading()),
            aCallScreenState(urlState = AsyncData.Failure(Exception("An error occurred"))),
            aCallScreenState(webViewError = "Error details from WebView"),
        )
}

internal fun aCallScreenState(
    urlState: AsyncData<String> = AsyncData.Success("https://call.orbitweb.tech/some-actual-call?with=parameters"),
    webViewError: String? = null,
    userAgent: String = "",
    isCallActive: Boolean = true,
    eventSink: (CallScreenEvent) -> Unit = {},
): CallScreenState {
    return CallScreenState(
        urlState = urlState,
        webViewError = webViewError,
        userAgent = userAgent,
        isCallActive = isCallActive,
        eventSink = eventSink,
    )
}
