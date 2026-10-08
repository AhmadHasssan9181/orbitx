/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.androidutils.uri

import android.net.Uri
import androidx.core.net.toUri

const val IGNORED_SCHEMA = "ignored"

fun createIgnoredUri(path: String): Uri = "$IGNORED_SCHEMA://$path".toUri()
