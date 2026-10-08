/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.test

import tech.orbitweb.orbitx.features.messages.api.MessageComposerContext
import tech.orbitweb.orbitx.libraries.textcomposer.model.MessageComposerMode

class FakeMessageComposerContext(
    override var composerMode: MessageComposerMode = MessageComposerMode.Normal
) : MessageComposerContext
