/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.dateformatter.impl

import dev.zacsweers.metro.Inject
import tech.orbitweb.orbitx.services.toolbox.api.strings.StringProvider

@Inject
class DateFormatterFull(
    private val stringProvider: StringProvider,
    private val localDateTimeProvider: LocalDateTimeProvider,
    private val dateFormatters: DateFormatters,
    private val dateFormatterDay: DateFormatterDay,
) {
    fun format(
        timestamp: Long,
        useRelative: Boolean,
    ): String {
        val dateToFormat = localDateTimeProvider.providesFromTimestamp(timestamp)
        val time = dateFormatters.formatTime(dateToFormat)
        return if (useRelative) {
            val now = localDateTimeProvider.providesNow()
            if (now.date == dateToFormat.date) {
               time
            } else {
                val dateStr = dateFormatterDay.format(timestamp, true)
                stringProvider.getString(R.string.common_date_date_at_time, dateStr, time)
            }
        } else {
            val dateStr = dateFormatters.formatDateWithFullFormat(dateToFormat)
            stringProvider.getString(R.string.common_date_date_at_time, dateStr, time)
        }
    }
}
