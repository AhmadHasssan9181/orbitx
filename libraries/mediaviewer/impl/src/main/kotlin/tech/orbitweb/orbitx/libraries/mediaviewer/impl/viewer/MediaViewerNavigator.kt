/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.mediaviewer.impl.viewer

import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId

interface MediaViewerNavigator {
    fun onViewInTimelineClick(eventId: EventId)
    fun onForwardClick(eventId: EventId, fromPinnedEvents: Boolean)
    fun onItemDeleted()
}
