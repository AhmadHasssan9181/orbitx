/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.messagecomposer

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import io.element.android.wysiwyg.display.TextDisplay
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.textcomposer.mentions.ResolvedSuggestion
import tech.orbitweb.orbitx.libraries.textcomposer.model.MessageComposerMode
import tech.orbitweb.orbitx.libraries.textcomposer.model.TextEditorState
import tech.orbitweb.orbitx.libraries.textcomposer.model.aTextEditorStateRich

open class MessageComposerStateProvider : PreviewParameterProvider<MessageComposerState> {
    override val values: Sequence<MessageComposerState>
        get() = sequenceOf(
            aMessageComposerState(),
        )
}

fun aMessageComposerState(
    textEditorState: TextEditorState = aTextEditorStateRich(),
    isFullScreen: Boolean = false,
    mode: MessageComposerMode = MessageComposerMode.Normal,
    showTextFormatting: Boolean = false,
    showAttachmentSourcePicker: Boolean = false,
    canShareLocation: Boolean = true,
    suggestions: ImmutableList<ResolvedSuggestion> = persistentListOf(),
    slashCommandAction: AsyncAction<Unit> = AsyncAction.Uninitialized,
    isViewOnce: Boolean = false,
    eventSink: (MessageComposerEvent) -> Unit = {},
) = MessageComposerState(
    textEditorState = textEditorState,
    isFullScreen = isFullScreen,
    mode = mode,
    showTextFormatting = showTextFormatting,
    showAttachmentSourcePicker = showAttachmentSourcePicker,
    canShareLocation = canShareLocation,
    suggestions = suggestions,
    resolveMentionDisplay = { _, _ -> TextDisplay.Plain },
    resolveAtRoomMentionDisplay = { TextDisplay.Plain },
    slashCommandAction = slashCommandAction,
    isViewOnce = isViewOnce,
    eventSink = eventSink,
)
