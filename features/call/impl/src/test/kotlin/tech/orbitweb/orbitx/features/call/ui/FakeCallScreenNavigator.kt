/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.call.ui

import tech.orbitweb.orbitx.features.call.impl.ui.CallScreenNavigator

class FakeCallScreenNavigator : CallScreenNavigator {
    var closeCalled = false
        private set

    override fun close() {
        closeCalled = true
    }
}
