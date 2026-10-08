/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.rageshake.impl.logs

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import tech.orbitweb.orbitx.features.rageshake.api.logs.LogFilesRemover
import tech.orbitweb.orbitx.features.rageshake.impl.reporter.DefaultBugReporter
import java.io.File

@ContributesBinding(AppScope::class)
class DefaultLogFilesRemover(
    private val bugReporter: DefaultBugReporter,
) : LogFilesRemover {
    override suspend fun perform(predicate: (File) -> Boolean) {
        bugReporter.deleteAllFiles(predicate)
    }
}
