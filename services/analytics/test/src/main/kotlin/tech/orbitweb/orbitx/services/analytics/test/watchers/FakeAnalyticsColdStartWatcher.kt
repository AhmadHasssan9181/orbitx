/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.services.analytics.test.watchers

import tech.orbitweb.orbitx.services.analytics.api.watchers.AnalyticsColdStartWatcher

class FakeAnalyticsColdStartWatcher : AnalyticsColdStartWatcher {
    override fun start() {}
    override fun whenLoggingIn() {}
    override fun onRoomListVisible() {}
}
