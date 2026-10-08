/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.rageshake.api.logs

import tech.orbitweb.orbitx.features.rageshake.api.reporter.BugReporter
import tech.orbitweb.orbitx.libraries.matrix.api.tracing.WriteToFilesConfiguration

fun BugReporter.createWriteToFilesConfiguration(): WriteToFilesConfiguration {
    return WriteToFilesConfiguration.Enabled(
        directory = logDirectory().absolutePath,
        filenamePrefix = "logs",
        // Keep a maximum of 1 week of log files.
        numberOfFiles = 7 * 24,
    )
}
