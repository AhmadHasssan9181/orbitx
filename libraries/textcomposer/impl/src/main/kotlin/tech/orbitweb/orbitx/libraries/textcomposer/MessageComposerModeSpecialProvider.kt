/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.textcomposer

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import tech.orbitweb.orbitx.libraries.matrix.ui.messages.reply.InReplyToDetailsProvider
import tech.orbitweb.orbitx.libraries.textcomposer.model.MessageComposerMode

class MessageComposerModeSpecialProvider : PreviewParameterProvider<MessageComposerMode.Special> {
    override val values: Sequence<MessageComposerMode.Special> = sequenceOf(
        aMessageComposerModeEdit()
    ) +
        // Keep only 3 values from InReplyToDetailsProvider
        InReplyToDetailsProvider().values.take(3).map {
            aMessageComposerModeReply(
                replyToDetails = it
            )
        }
}
