/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.poll.impl.history.model

import tech.orbitweb.orbitx.features.poll.api.pollcontent.PollContentState

data class PollHistoryItem(
    val formattedDate: String,
    val state: PollContentState,
)
