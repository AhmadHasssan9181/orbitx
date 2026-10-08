/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.services.analytics.api.watchers

/**
 * An analytics watcher tracking the time it took the client to send a message.
 */
interface AnalyticsSendMessageWatcher {
    /**
     * Start listening to send queue updates and tracking the sending states of the events.
     */
    fun start()

    /**
     * Stop observing the sending states of the events.
     */
    fun stop()
}
