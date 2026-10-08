/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.timeline.components

enum class TimestampPosition {
    /**
     * Timestamp should overlay the timeline event content (eg. image).
     */
    Overlay,

    /**
     * Timestamp should be aligned with the timeline event content if this is possible (eg. text).
     */
    Aligned,

    /**
     * Timestamp should always be rendered below the timeline event content (eg. poll).
     */
    Below,

    /**
     * Timestamp should be hidden.
     */
    Hidden;

    companion object {
        /**
         * Default timestamp position for timeline event contents.
         */
        val Default: TimestampPosition = Aligned
    }
}
