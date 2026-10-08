/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.architecture

import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.plugin.Plugin
import com.bumble.appyx.core.plugin.plugins

interface NodeInputs : Plugin

inline fun <reified I : NodeInputs> Node.inputs(): I {
    return requireNotNull(plugins<I>().firstOrNull()) { "Make sure to actually pass NodeInputs plugin to your node" }
}
