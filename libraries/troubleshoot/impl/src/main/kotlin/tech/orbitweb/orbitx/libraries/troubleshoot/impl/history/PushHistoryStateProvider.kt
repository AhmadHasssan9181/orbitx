/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.troubleshoot.impl.history

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId
import tech.orbitweb.orbitx.libraries.push.api.history.PushHistoryItem
import kotlinx.collections.immutable.toImmutableList

open class PushHistoryStateProvider : PreviewParameterProvider<PushHistoryState> {
    override val values: Sequence<PushHistoryState>
        get() = sequenceOf(
            aPushHistoryState(),
            aPushHistoryState(
                pushCounter = 123,
                pushHistoryItems = listOf(
                    aPushHistoryItem(
                        hasBeenResolved = false,
                        comment = "An error description"
                    ),
                    aPushHistoryItem(
                        pushDate = 1,
                        providerInfo = "providerInfo2",
                        eventId = EventId("\$anEventId"),
                        roomId = RoomId("!roomId:domain"),
                        sessionId = SessionId("@alice:server.org"),
                        hasBeenResolved = true,
                        comment = "A comment"
                    )
                )
            ),
            aPushHistoryState(
                resetAction = AsyncAction.ConfirmingNoParams,
            ),
            aPushHistoryState(
                showNotSameAccountError = true,
            ),
        )
}

fun aPushHistoryState(
    pushCounter: Int = 0,
    pushHistoryItems: List<PushHistoryItem> = emptyList(),
    showOnlyErrors: Boolean = false,
    resetAction: AsyncAction<Unit> = AsyncAction.Uninitialized,
    showNotSameAccountError: Boolean = false,
    eventSink: (PushHistoryEvents) -> Unit = {},
) = PushHistoryState(
    pushCounter = pushCounter,
    pushHistoryItems = pushHistoryItems.toImmutableList(),
    showOnlyErrors = showOnlyErrors,
    resetAction = resetAction,
    showNotSameAccountError = showNotSameAccountError,
    eventSink = eventSink,
)

fun aPushHistoryItem(
    pushDate: Long = 0,
    formattedDate: String = "formattedDate",
    providerInfo: String = "providerInfo",
    eventId: EventId? = null,
    roomId: RoomId? = null,
    sessionId: SessionId? = null,
    hasBeenResolved: Boolean = false,
    comment: String? = null,
): PushHistoryItem {
    return PushHistoryItem(
        pushDate = pushDate,
        formattedDate = formattedDate,
        providerInfo = providerInfo,
        eventId = eventId,
        roomId = roomId,
        sessionId = sessionId,
        hasBeenResolved = hasBeenResolved,
        comment = comment
    )
}
