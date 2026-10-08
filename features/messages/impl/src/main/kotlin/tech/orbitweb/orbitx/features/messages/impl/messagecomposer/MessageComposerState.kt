/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.messagecomposer

import androidx.compose.runtime.Stable
import io.element.android.wysiwyg.display.TextDisplay
import kotlinx.collections.immutable.ImmutableList
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.textcomposer.mentions.ResolvedSuggestion
import tech.orbitweb.orbitx.libraries.textcomposer.model.MessageComposerMode
import tech.orbitweb.orbitx.libraries.textcomposer.model.TextEditorState

@Stable
data class MessageComposerState(
    val textEditorState: TextEditorState,
    val isFullScreen: Boolean,
    val mode: MessageComposerMode,
    val showAttachmentSourcePicker: Boolean,
    val showTextFormatting: Boolean,
    val canShareLocation: Boolean,
    val suggestions: ImmutableList<ResolvedSuggestion>,
    val resolveMentionDisplay: (String, String) -> TextDisplay,
    val resolveAtRoomMentionDisplay: () -> TextDisplay,
    val slashCommandAction: AsyncAction<Unit>,
    val isViewOnce: Boolean,
    val eventSink: (MessageComposerEvent) -> Unit,
)
