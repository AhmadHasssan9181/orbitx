/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.androidutils.browser

import android.util.Log
import android.webkit.ConsoleMessage
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import timber.log.Timber

interface ConsoleMessageLogger {
    fun log(
        tag: String,
        consoleMessage: ConsoleMessage,
    )
}

@ContributesBinding(AppScope::class)
class DefaultConsoleMessageLogger : ConsoleMessageLogger {
    override fun log(
        tag: String,
        consoleMessage: ConsoleMessage,
    ) {
        val priority = when (consoleMessage.messageLevel()) {
            ConsoleMessage.MessageLevel.ERROR -> Log.ERROR
            ConsoleMessage.MessageLevel.WARNING -> Log.WARN
            else -> Log.DEBUG
        }

        // Avoid logging any messages that contain "password" to prevent leaking sensitive information
        if (consoleMessage.message().contains("password=")) {
            return
        }

        val message = buildString {
            append(consoleMessage.sourceId())
            append(":")
            append(consoleMessage.lineNumber())
            append(" ")
            append(consoleMessage.message())
        }

        Timber.tag(tag).log(priority = priority, message = message)
    }
}
