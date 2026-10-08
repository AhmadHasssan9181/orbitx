/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.rageshake.impl.bugreport

import android.os.Parcelable
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import kotlinx.parcelize.Parcelize

data class BugReportState(
    val formState: BugReportFormState,
    val hasCrashLogs: Boolean,
    val screenshotUri: String?,
    val sendingProgress: Float,
    val sending: AsyncAction<Unit>,
    val eventSink: (BugReportEvents) -> Unit
) {
    val submitEnabled = sending !is AsyncAction.Loading
    val isDescriptionInError = sending is AsyncAction.Failure &&
        sending.error is BugReportFormError.DescriptionTooShort
}

@Parcelize
data class BugReportFormState(
    val description: String,
    val sendLogs: Boolean,
    val canContact: Boolean,
    val sendScreenshot: Boolean,
    val sendPushRules: Boolean,
    val ghIssueNumber: Int?,
) : Parcelable {
    companion object {
        val Default = BugReportFormState(
            description = "",
            sendLogs = true,
            canContact = false,
            sendScreenshot = false,
            sendPushRules = false,
            ghIssueNumber = null,
        )
    }
}
