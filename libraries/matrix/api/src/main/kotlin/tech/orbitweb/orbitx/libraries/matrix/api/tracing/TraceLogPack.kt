/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.api.tracing

enum class TraceLogPack(val key: String) {
    EVENT_CACHE("event_cache") {
        override val title: String = "Event Cache"
    },
    SEND_QUEUE("send_queue") {
        override val title: String = "Send Queue"
    },
    TIMELINE("timeline") {
        override val title: String = "Timeline"
    },
    NOTIFICATION_CLIENT("notification_client") {
        override val title: String = "Notification Client"
    },
    SYNC_PROFILING("sync_profiling") {
        override val title: String = "Sync Profiling"
    },
    LATEST_EVENTS("latest_events") {
        override val title = "Latest Events"
    };

    abstract val title: String
}
