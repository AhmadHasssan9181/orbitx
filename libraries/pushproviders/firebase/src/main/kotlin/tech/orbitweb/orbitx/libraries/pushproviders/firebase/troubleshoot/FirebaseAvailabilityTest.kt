/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.pushproviders.firebase.troubleshoot

import dev.zacsweers.metro.ContributesIntoSet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import tech.orbitweb.orbitx.libraries.di.SessionScope
import tech.orbitweb.orbitx.libraries.pushproviders.firebase.FirebaseConfig
import tech.orbitweb.orbitx.libraries.pushproviders.firebase.IsPlayServiceAvailable
import tech.orbitweb.orbitx.libraries.pushproviders.firebase.R
import tech.orbitweb.orbitx.libraries.troubleshoot.api.test.NotificationTroubleshootTest
import tech.orbitweb.orbitx.libraries.troubleshoot.api.test.NotificationTroubleshootTestDelegate
import tech.orbitweb.orbitx.libraries.troubleshoot.api.test.NotificationTroubleshootTestState
import tech.orbitweb.orbitx.libraries.troubleshoot.api.test.TestFilterData
import tech.orbitweb.orbitx.services.toolbox.api.strings.StringProvider

@ContributesIntoSet(SessionScope::class)
class FirebaseAvailabilityTest(
    private val isPlayServiceAvailable: IsPlayServiceAvailable,
    private val stringProvider: StringProvider,
) : NotificationTroubleshootTest {
    override val order = 300
    private val delegate = NotificationTroubleshootTestDelegate(
        defaultName = stringProvider.getString(R.string.troubleshoot_notifications_test_firebase_availability_title),
        defaultDescription = stringProvider.getString(R.string.troubleshoot_notifications_test_firebase_availability_description),
        visibleWhenIdle = false,
        fakeDelay = NotificationTroubleshootTestDelegate.LONG_DELAY,
    )
    override val state: StateFlow<NotificationTroubleshootTestState> = delegate.state

    override fun isRelevant(data: TestFilterData): Boolean {
        return data.currentPushProviderName == FirebaseConfig.NAME
    }

    override suspend fun run(coroutineScope: CoroutineScope) {
        delegate.start()
        val result = isPlayServiceAvailable.isAvailable()
        if (result) {
            delegate.updateState(
                description = stringProvider.getString(R.string.troubleshoot_notifications_test_firebase_availability_success),
                status = NotificationTroubleshootTestState.Status.Success
            )
        } else {
            delegate.updateState(
                description = stringProvider.getString(R.string.troubleshoot_notifications_test_firebase_availability_failure),
                status = NotificationTroubleshootTestState.Status.Failure()
            )
        }
    }

    override suspend fun reset() = delegate.reset()
}
