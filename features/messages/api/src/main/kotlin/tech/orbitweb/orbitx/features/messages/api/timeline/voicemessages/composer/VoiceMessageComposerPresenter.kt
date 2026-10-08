/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.api.timeline.voicemessages.composer

import tech.orbitweb.orbitx.libraries.architecture.Presenter
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.Timeline

fun interface VoiceMessageComposerPresenter : Presenter<VoiceMessageComposerState> {
    interface Factory {
        fun create(timelineMode: Timeline.Mode): VoiceMessageComposerPresenter
    }
}
