/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.api.timeline.voicemessages.composer

import androidx.compose.runtime.Stable
import tech.orbitweb.orbitx.libraries.textcomposer.model.VoiceMessageState

@Stable
data class VoiceMessageComposerState(
    val voiceMessageState: VoiceMessageState,
    val showPermissionRationaleDialog: Boolean,
    val showSendFailureDialog: Boolean,
    val keepScreenOn: Boolean,
    val eventSink: (VoiceMessageComposerEvent) -> Unit,
)
