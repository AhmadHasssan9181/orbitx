/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.preferences.impl.developer.tracing

import tech.orbitweb.orbitx.libraries.matrix.api.tracing.LogLevel

fun LogLevelItem.toLogLevel(): LogLevel {
    return when (this) {
        LogLevelItem.ERROR -> tech.orbitweb.orbitx.libraries.matrix.api.tracing.LogLevel.ERROR
        LogLevelItem.WARN -> tech.orbitweb.orbitx.libraries.matrix.api.tracing.LogLevel.WARN
        LogLevelItem.INFO -> tech.orbitweb.orbitx.libraries.matrix.api.tracing.LogLevel.INFO
        LogLevelItem.DEBUG -> tech.orbitweb.orbitx.libraries.matrix.api.tracing.LogLevel.DEBUG
        LogLevelItem.TRACE -> tech.orbitweb.orbitx.libraries.matrix.api.tracing.LogLevel.TRACE
    }
}

fun LogLevel.toLogLevelItem(): LogLevelItem {
    return when (this) {
        LogLevel.ERROR -> LogLevelItem.ERROR
        LogLevel.WARN -> LogLevelItem.WARN
        LogLevel.INFO -> LogLevelItem.INFO
        LogLevel.DEBUG -> LogLevelItem.DEBUG
        LogLevel.TRACE -> LogLevelItem.TRACE
    }
}
