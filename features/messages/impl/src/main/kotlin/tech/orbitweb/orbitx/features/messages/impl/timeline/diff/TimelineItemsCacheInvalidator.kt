/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.timeline.diff

import tech.orbitweb.orbitx.features.messages.impl.timeline.model.TimelineItem
import tech.orbitweb.orbitx.libraries.androidutils.diff.DefaultDiffCacheInvalidator
import tech.orbitweb.orbitx.libraries.androidutils.diff.DiffCacheInvalidator
import tech.orbitweb.orbitx.libraries.androidutils.diff.MutableDiffCache

/**
 * [DiffCacheInvalidator] implementation for [TimelineItem].
 * It uses [DefaultDiffCacheInvalidator] and invalidate the cache around the updated item so that those items are computed again.
 * This is needed because a timeline item is computed based on the previous and next items.
 */
internal class TimelineItemsCacheInvalidator : DiffCacheInvalidator<TimelineItem> {
    private val delegate = DefaultDiffCacheInvalidator<TimelineItem>()

    override fun onChanged(position: Int, count: Int, cache: MutableDiffCache<TimelineItem>) {
        delegate.onChanged(position, count, cache)
    }

    override fun onMoved(fromPosition: Int, toPosition: Int, cache: MutableDiffCache<TimelineItem>) {
        delegate.onMoved(fromPosition, toPosition, cache)
    }

    override fun onInserted(position: Int, count: Int, cache: MutableDiffCache<TimelineItem>) {
        cache.invalidateAround(position)
        delegate.onInserted(position, count, cache)
    }

    override fun onRemoved(position: Int, count: Int, cache: MutableDiffCache<TimelineItem>) {
        cache.invalidateAround(position)
        delegate.onRemoved(position, count, cache)
    }
}

/**
 * Invalidate the cache around the given position.
 * It invalidates the previous and next items.
 */
private fun MutableDiffCache<*>.invalidateAround(position: Int) {
    if (position > 0) {
        set(position - 1, null)
    }
    if (position < indices().last) {
        set(position + 1, null)
    }
}
