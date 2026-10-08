/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.pinned.banner

import androidx.compose.ui.text.AnnotatedString
import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId

data class PinnedMessagesBannerItem(
    val eventId: EventId,
    val formatted: AnnotatedString,
)
