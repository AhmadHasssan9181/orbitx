/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.fixtures.factories

import org.matrix.rustcomponents.sdk.EventTimelineItemDebugInfo

internal fun anEventTimelineItemDebugInfo(
    model: String = "model",
    originalJson: String? = null,
    latestEditJson: String? = null,
) = EventTimelineItemDebugInfo(
    model = model,
    originalJson = originalJson,
    latestEditJson = latestEditJson
)
