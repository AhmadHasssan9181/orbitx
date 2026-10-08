/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.lockscreen.impl

import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import tech.orbitweb.orbitx.features.lockscreen.impl.unlock.activity.PinUnlockActivity
import tech.orbitweb.orbitx.tests.testutils.robolectric.RobolectricTest

class DefaultLockScreenEntryPointIntentTest : RobolectricTest() {
    @Test
    fun `test pin unlock intent`() {
        val entryPoint = DefaultLockScreenEntryPoint()
        val result = entryPoint.pinUnlockIntent(InstrumentationRegistry.getInstrumentation().context)
        assertThat(result.component?.className).isEqualTo(PinUnlockActivity::class.qualifiedName)
    }
}
