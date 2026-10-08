/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.link

import io.element.android.wysiwyg.link.Link
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction

data class LinkState(
    val linkClick: AsyncAction<Link>,
    val eventSink: (LinkEvent) -> Unit,
)
