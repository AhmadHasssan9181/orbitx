/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.rageshake.impl.reporter

import kotlinx.coroutines.flow.Flow
import okhttp3.HttpUrl

fun interface BugReporterUrlProvider {
    fun provide(): Flow<HttpUrl?>
}
