/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.timeline.debug

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.plugin.Plugin
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import tech.orbitweb.orbitx.annotations.ContributesNode
import tech.orbitweb.orbitx.libraries.architecture.NodeInputs
import tech.orbitweb.orbitx.libraries.architecture.inputs
import tech.orbitweb.orbitx.libraries.di.RoomScope
import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.TimelineItemDebugInfo

@ContributesNode(RoomScope::class)
@AssistedInject
class EventDebugInfoNode(
    @Assisted buildContext: BuildContext,
    @Assisted plugins: List<Plugin>,
) : Node(buildContext, plugins = plugins) {
    data class Inputs(
        val eventId: EventId?,
        val timelineItemDebugInfo: TimelineItemDebugInfo,
    ) : NodeInputs

    private val inputs = inputs<Inputs>()

    private fun onBackClick() {
        navigateUp()
    }

    @Composable
    override fun View(modifier: Modifier) = with(inputs) {
        EventDebugInfoView(
            eventId = eventId,
            model = timelineItemDebugInfo.model,
            originalJson = timelineItemDebugInfo.originalJson,
            latestEditedJson = timelineItemDebugInfo.latestEditedJson,
            onBackClick = ::onBackClick
        )
    }
}
