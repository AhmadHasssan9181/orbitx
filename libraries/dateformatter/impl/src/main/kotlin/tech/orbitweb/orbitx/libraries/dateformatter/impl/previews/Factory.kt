/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.dateformatter.impl.previews

import android.content.Context
import tech.orbitweb.orbitx.libraries.dateformatter.impl.DateFormatterFull
import tech.orbitweb.orbitx.libraries.dateformatter.impl.DateFormatterMonth
import tech.orbitweb.orbitx.libraries.dateformatter.impl.DateFormatterTime
import tech.orbitweb.orbitx.libraries.dateformatter.impl.DateFormatterTimeOnly
import tech.orbitweb.orbitx.libraries.dateformatter.impl.DateFormatters
import tech.orbitweb.orbitx.libraries.dateformatter.impl.DefaultDateFormatter
import tech.orbitweb.orbitx.libraries.dateformatter.impl.DefaultDateFormatterDay
import tech.orbitweb.orbitx.libraries.dateformatter.impl.LocalDateTimeProvider
import kotlinx.datetime.TimeZone
import java.util.Locale
import kotlin.time.Instant

/**
 * Create DefaultDateFormatter and set current time to the provided date.
 */
fun createFormatter(
    context: Context,
    currentDate: String,
    locale: Locale,
): DefaultDateFormatter {
    val clock = PreviewClock().apply { givenInstant(Instant.parse(currentDate)) }
    val localDateTimeProvider = LocalDateTimeProvider(clock) { TimeZone.UTC }
    val dateFormatters = DateFormatters(
        localeChangeObserver = {},
        clock = clock,
        timeZoneProvider = { TimeZone.UTC },
        locale = locale,
    )
    val stringProvider = PreviewStringProvider(context.resources)
    val dateFormatterDay = DefaultDateFormatterDay(
        localDateTimeProvider = localDateTimeProvider,
        dateFormatters = dateFormatters,
    )
    return DefaultDateFormatter(
        dateFormatterFull = DateFormatterFull(
            stringProvider = stringProvider,
            localDateTimeProvider = localDateTimeProvider,
            dateFormatters = dateFormatters,
            dateFormatterDay = dateFormatterDay,
        ),
        dateFormatterMonth = DateFormatterMonth(
            stringProvider = stringProvider,
            localDateTimeProvider = localDateTimeProvider,
            dateFormatters = dateFormatters,
        ),
        dateFormatterDay = dateFormatterDay,
        dateFormatterTime = DateFormatterTime(
            localDateTimeProvider = localDateTimeProvider,
            dateFormatters = dateFormatters,
        ),
        dateFormatterTimeOnly = DateFormatterTimeOnly(
            localDateTimeProvider = localDateTimeProvider,
            dateFormatters = dateFormatters,
        ),
    )
}
