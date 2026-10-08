/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.rageshake.impl.bugreport

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction

open class BugReportStateProvider : PreviewParameterProvider<BugReportState> {
    override val values: Sequence<BugReportState>
        get() = sequenceOf(
            aBugReportState(),
            aBugReportState().copy(
                formState = BugReportFormState.Default.copy(
                    description = "A long enough description",
                    sendScreenshot = true,
                ),
                hasCrashLogs = true,
                screenshotUri = "aUri"
            ),
            aBugReportState().copy(sending = AsyncAction.Loading),
            aBugReportState().copy(sending = AsyncAction.Success(Unit)),
            aBugReportState().copy(sending = AsyncAction.Failure(BugReportFormError.DescriptionTooShort)),
        )
}

fun aBugReportState() = BugReportState(
    formState = BugReportFormState.Default,
    hasCrashLogs = false,
    screenshotUri = null,
    sendingProgress = 0F,
    sending = AsyncAction.Uninitialized,
    eventSink = {}
)
