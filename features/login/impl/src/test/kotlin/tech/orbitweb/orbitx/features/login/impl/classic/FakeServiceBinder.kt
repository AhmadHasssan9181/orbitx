/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.login.impl.classic

import android.content.Intent
import android.content.ServiceConnection
import tech.orbitweb.orbitx.libraries.androidutils.service.ServiceBinder
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError

class FakeServiceBinder(
    private val bindServiceResult: () -> Boolean = { lambdaError() },
    private val unbindServiceResult: () -> Unit = { lambdaError() },
) : ServiceBinder {
    override fun bindService(service: Intent, conn: ServiceConnection, flags: Int): Boolean {
        return bindServiceResult()
    }

    override fun unbindService(conn: ServiceConnection) {
        unbindServiceResult()
    }
}
