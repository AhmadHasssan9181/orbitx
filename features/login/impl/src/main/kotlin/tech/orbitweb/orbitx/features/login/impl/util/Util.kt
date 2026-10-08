/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.login.impl.util

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import tech.orbitweb.orbitx.appconfig.AuthenticationConfig
import tech.orbitweb.orbitx.libraries.core.data.tryOrNull

fun openLearnMorePage(context: Context) {
    val intent = Intent(Intent.ACTION_VIEW, AuthenticationConfig.SLIDING_SYNC_READ_MORE_URL.toUri())
    tryOrNull { context.startActivity(intent) }
}
