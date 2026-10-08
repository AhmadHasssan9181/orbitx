/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.mediaviewer.test.util

import tech.orbitweb.orbitx.libraries.mediaviewer.api.util.FileExtensionExtractor

class FileExtensionExtractorWithoutValidation : FileExtensionExtractor {
    override fun extractFromName(name: String): String {
        return name.substringAfterLast('.', "")
    }
}
