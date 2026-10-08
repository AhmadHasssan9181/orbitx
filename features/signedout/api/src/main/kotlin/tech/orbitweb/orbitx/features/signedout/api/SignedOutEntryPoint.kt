/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.signedout.api

import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import tech.orbitweb.orbitx.libraries.architecture.FeatureEntryPoint
import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId

interface SignedOutEntryPoint : FeatureEntryPoint {
    data class Params(
        val sessionId: SessionId,
    )

    fun createNode(
        parentNode: Node,
        buildContext: BuildContext,
        params: Params,
    ): Node
}
