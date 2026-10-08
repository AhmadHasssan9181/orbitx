/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.dateformatter.test

import tech.orbitweb.orbitx.libraries.dateformatter.api.DateFormatter
import tech.orbitweb.orbitx.libraries.dateformatter.api.DateFormatterMode

class FakeDateFormatter(
    private val formatLambda: (Long?, DateFormatterMode, Boolean) -> String = { timestamp, mode, useRelative ->
        "$timestamp $mode $useRelative"
    },
) : DateFormatter {
    override fun format(
        timestamp: Long?,
        mode: DateFormatterMode,
        useRelative: Boolean,
    ): String {
        return formatLambda(timestamp, mode, useRelative)
    }
}
