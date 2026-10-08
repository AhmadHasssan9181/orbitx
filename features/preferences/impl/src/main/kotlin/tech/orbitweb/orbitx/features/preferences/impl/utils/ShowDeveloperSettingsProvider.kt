/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.preferences.impl.utils

import dev.zacsweers.metro.Inject
import tech.orbitweb.orbitx.libraries.core.meta.BuildMeta
import tech.orbitweb.orbitx.libraries.core.meta.BuildType
import tech.orbitweb.orbitx.libraries.ui.utils.MultipleTapToUnlock
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Inject
class ShowDeveloperSettingsProvider(
    buildMeta: BuildMeta,
) {
    companion object {
        const val DEVELOPER_SETTINGS_COUNTER = 7
    }

    private val multipleTapToUnlock = MultipleTapToUnlock(DEVELOPER_SETTINGS_COUNTER)
    private val isDeveloperBuild = buildMeta.buildType != BuildType.RELEASE

    private val _showDeveloperSettings = MutableStateFlow(isDeveloperBuild)
    val showDeveloperSettings: StateFlow<Boolean> = _showDeveloperSettings

    fun unlockDeveloperSettings(scope: CoroutineScope) {
        if (multipleTapToUnlock.unlock(scope)) {
            _showDeveloperSettings.value = true
        }
    }
}
