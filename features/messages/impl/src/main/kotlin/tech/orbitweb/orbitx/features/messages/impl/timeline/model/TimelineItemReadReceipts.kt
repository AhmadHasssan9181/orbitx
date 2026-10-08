/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.timeline.model

import tech.orbitweb.orbitx.libraries.designsystem.components.avatar.AvatarData
import kotlinx.collections.immutable.ImmutableList

data class TimelineItemReadReceipts(
    val receipts: ImmutableList<ReadReceiptData>,
)

data class ReadReceiptData(
    val avatarData: AvatarData,
    val formattedDate: String,
)
