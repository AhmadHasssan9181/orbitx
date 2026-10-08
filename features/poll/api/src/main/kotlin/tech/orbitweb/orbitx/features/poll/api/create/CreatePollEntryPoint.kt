/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.poll.api.create

import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import tech.orbitweb.orbitx.libraries.architecture.FeatureEntryPoint
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.Timeline

interface CreatePollEntryPoint : FeatureEntryPoint {
    data class Params(
        val timelineMode: Timeline.Mode,
        val mode: CreatePollMode,
    )

    fun createNode(
        parentNode: Node,
        buildContext: BuildContext,
        params: Params,
    ): Node
}
