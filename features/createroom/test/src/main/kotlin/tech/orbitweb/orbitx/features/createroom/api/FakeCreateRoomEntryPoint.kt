/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.createroom.api

import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError

class FakeCreateRoomEntryPoint : CreateRoomEntryPoint {
    class Builder : CreateRoomEntryPoint.Builder {
        override fun setIsSpace(isSpace: Boolean): Builder = this
        override fun setParentSpace(parentSpaceId: RoomId): Builder = this
        override fun build(): Node = lambdaError()
    }

    override fun builder(
        parentNode: Node,
        buildContext: BuildContext,
        callback: CreateRoomEntryPoint.Callback,
    ): Builder = lambdaError()
}
