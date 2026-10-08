/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.troubleshoot.impl.history

import kotlinx.collections.immutable.ImmutableList
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.push.api.history.PushHistoryItem

data class PushHistoryState(
    val pushCounter: Int,
    val pushHistoryItems: ImmutableList<PushHistoryItem>,
    val showOnlyErrors: Boolean,
    val resetAction: AsyncAction<Unit>,
    val showNotSameAccountError: Boolean,
    val eventSink: (PushHistoryEvents) -> Unit,
)
