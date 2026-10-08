/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.fixtures.fakes

import org.matrix.rustcomponents.sdk.EventTimelineItemDebugInfo
import org.matrix.rustcomponents.sdk.LazyTimelineItemProvider
import org.matrix.rustcomponents.sdk.NoHandle
import org.matrix.rustcomponents.sdk.SendHandle
import org.matrix.rustcomponents.sdk.ShieldState
import tech.orbitweb.orbitx.libraries.matrix.impl.fixtures.factories.anEventTimelineItemDebugInfo

class FakeFfiLazyTimelineItemProvider(
    private val debugInfo: EventTimelineItemDebugInfo = anEventTimelineItemDebugInfo(),
    private val shieldsState: ShieldState = ShieldState.None,
) : LazyTimelineItemProvider(NoHandle) {
    override fun getShields(strict: Boolean) = shieldsState
    override fun debugInfo() = debugInfo
    override fun getSendHandle(): SendHandle? = null
}
