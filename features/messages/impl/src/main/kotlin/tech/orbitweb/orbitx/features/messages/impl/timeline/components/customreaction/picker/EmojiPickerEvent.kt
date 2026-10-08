/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.timeline.components.customreaction.picker

sealed interface EmojiPickerEvent {
    data class ToggleSearchActive(val isActive: Boolean) : EmojiPickerEvent
}
