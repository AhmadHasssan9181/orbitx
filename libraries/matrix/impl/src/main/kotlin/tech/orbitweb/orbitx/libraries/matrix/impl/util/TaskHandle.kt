/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.util

import org.matrix.rustcomponents.sdk.TaskHandle
import java.util.concurrent.CopyOnWriteArraySet

fun TaskHandle.cancelAndDestroy() {
    cancel()
    destroy()
}

class TaskHandleBag(private val taskHandles: MutableSet<TaskHandle> = CopyOnWriteArraySet()) : Set<TaskHandle> by taskHandles {
    operator fun plusAssign(taskHandle: TaskHandle?) {
        if (taskHandle == null) return
        taskHandles += taskHandle
    }

    fun dispose() {
        taskHandles.forEach {
            it.cancelAndDestroy()
        }
        taskHandles.clear()
    }
}
