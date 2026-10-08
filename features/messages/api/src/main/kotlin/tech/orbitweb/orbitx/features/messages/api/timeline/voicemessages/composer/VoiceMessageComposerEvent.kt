/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.api.timeline.voicemessages.composer

import androidx.lifecycle.Lifecycle
import tech.orbitweb.orbitx.libraries.textcomposer.model.VoiceMessagePlayerEvent
import tech.orbitweb.orbitx.libraries.textcomposer.model.VoiceMessageRecorderEvent

sealed interface VoiceMessageComposerEvent {
    data class RecorderEvent(
        val recorderEvent: VoiceMessageRecorderEvent
    ) : VoiceMessageComposerEvent
    data class PlayerEvent(
        val playerEvent: VoiceMessagePlayerEvent,
    ) : VoiceMessageComposerEvent
    data object SendVoiceMessage : VoiceMessageComposerEvent
    data object DeleteVoiceMessage : VoiceMessageComposerEvent
    data object AcceptPermissionRationale : VoiceMessageComposerEvent
    data object DismissPermissionsRationale : VoiceMessageComposerEvent
    data class LifecycleEvent(val event: Lifecycle.Event) : VoiceMessageComposerEvent
    data object DismissSendFailureDialog : VoiceMessageComposerEvent
}
