/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.timeline.components.receipt.bottomsheet

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import tech.orbitweb.orbitx.features.messages.impl.timeline.aTimelineItemEvent
import tech.orbitweb.orbitx.features.messages.impl.timeline.components.receipt.ReadReceiptViewStateProvider
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.TimelineItemReadReceipts
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.LocalEventSendState
import kotlinx.collections.immutable.toImmutableList

class ReadReceiptBottomSheetStateProvider : PreviewParameterProvider<ReadReceiptBottomSheetState> {
    // Reuse the provider ReadReceiptViewStateProvider
    private val readReceiptViewStateProvider = ReadReceiptViewStateProvider()
    override val values: Sequence<ReadReceiptBottomSheetState> = readReceiptViewStateProvider.values
        .filter { it.sendState is LocalEventSendState.Sent }
        .map { readReceiptViewState ->
            ReadReceiptBottomSheetState(
                selectedEvent = aTimelineItemEvent(
                    readReceiptState = TimelineItemReadReceipts(
                        receipts = readReceiptViewState.receipts.map { readReceiptData ->
                            readReceiptData
                                .copy(avatarData = readReceiptData.avatarData.copy(id = "@${readReceiptData.avatarData.id}:localhost"))
                        }.toImmutableList()
                    )
                ),
                eventSink = {},
            )
        }
}
