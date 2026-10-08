/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.intent

import android.app.Activity
import androidx.compose.ui.platform.UriHandler
import tech.orbitweb.orbitx.libraries.androidutils.system.openUrlInExternalApp

class SafeUriHandler(private val activity: Activity) : UriHandler {
    override fun openUri(uri: String) {
        activity.openUrlInExternalApp(uri)
    }
}
