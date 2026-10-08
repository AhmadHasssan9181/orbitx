/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.api

import tech.orbitweb.orbitx.libraries.textcomposer.model.MessageComposerMode

/**
 * Hoist-able state of the message composer.
 *
 * Typical use case is inside other presenters, to know if
 * the composer is in a thread, if it's editing a message, etc.
 */
interface MessageComposerContext {
    val composerMode: MessageComposerMode
    var isViewOnce: Boolean
}
