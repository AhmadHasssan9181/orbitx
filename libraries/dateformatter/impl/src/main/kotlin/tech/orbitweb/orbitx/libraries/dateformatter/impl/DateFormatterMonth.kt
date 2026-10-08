/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.dateformatter.impl

import dev.zacsweers.metro.Inject
import tech.orbitweb.orbitx.libraries.core.extensions.safeCapitalize
import tech.orbitweb.orbitx.services.toolbox.api.strings.StringProvider

@Inject
class DateFormatterMonth(
    private val stringProvider: StringProvider,
    private val localDateTimeProvider: LocalDateTimeProvider,
    private val dateFormatters: DateFormatters,
) {
    fun format(
        timestamp: Long,
        useRelative: Boolean,
    ): String {
        val today = localDateTimeProvider.providesNow()
        val dateToFormat = localDateTimeProvider.providesFromTimestamp(timestamp)
        return if (useRelative && dateToFormat.month == today.month && dateToFormat.year == today.year) {
            stringProvider.getString(R.string.common_date_this_month)
        } else {
            dateFormatters.formatDateWithMonthAndYear(dateToFormat)
        }
            .safeCapitalize()
    }
}
