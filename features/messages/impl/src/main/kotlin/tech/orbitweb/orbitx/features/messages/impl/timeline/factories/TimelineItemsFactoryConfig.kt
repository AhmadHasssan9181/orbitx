/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.timeline.factories

/**
 * Some data used to configure the creation of timeline items.
 * @param computeReadReceipts when false, read receipts will be empty.
 * @param computeReactions when false, reactions will be empty.
 */
data class TimelineItemsFactoryConfig(
    val computeReadReceipts: Boolean,
    val computeReactions: Boolean,
)
