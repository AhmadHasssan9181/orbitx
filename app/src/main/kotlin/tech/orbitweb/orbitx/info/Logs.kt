/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.info

import android.content.Context
import tech.orbitweb.orbitx.libraries.androidutils.system.getVersionCodeFromManifest
import tech.orbitweb.orbitx.BuildConfig
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun logApplicationInfo(context: Context) {
    val appVersion = buildString {
        append(BuildConfig.VERSION_NAME)
        append(" (")
        append(context.getVersionCodeFromManifest())
        append(") - ")
        append(BuildConfig.BUILD_TYPE)
        append(" / ")
        append(BuildConfig.FLAVOR)
    }
    // TODO Get SDK version somehow
    val sdkVersion = "SDK VERSION (TODO)"
    val date = SimpleDateFormat("MM-dd HH:mm:ss.SSSZ", Locale.US).format(Date())

    Timber.d("----------------------------------------------------------------")
    Timber.d("----------------------------------------------------------------")
    Timber.d(" Application version: $appVersion")
    Timber.d(" Git SHA: ${BuildConfig.GIT_REVISION}")
    Timber.d(" SDK version: $sdkVersion")
    Timber.d(" Local time: $date")
    Timber.d("----------------------------------------------------------------")
    Timber.d("----------------------------------------------------------------\n\n\n\n")
}
