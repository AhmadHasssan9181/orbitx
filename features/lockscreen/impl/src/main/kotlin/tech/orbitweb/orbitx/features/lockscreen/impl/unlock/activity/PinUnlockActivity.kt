/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.lockscreen.impl.unlock.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.lifecycleScope
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.launch
import tech.orbitweb.orbitx.compound.colors.SemanticColorsLightDark
import tech.orbitweb.orbitx.features.enterprise.api.EnterpriseService
import tech.orbitweb.orbitx.features.lockscreen.api.LockScreenLockState
import tech.orbitweb.orbitx.features.lockscreen.api.LockScreenService
import tech.orbitweb.orbitx.features.lockscreen.impl.unlock.PinUnlockPresenter
import tech.orbitweb.orbitx.features.lockscreen.impl.unlock.PinUnlockView
import tech.orbitweb.orbitx.features.lockscreen.impl.unlock.di.PinUnlockBindings
import tech.orbitweb.orbitx.libraries.architecture.bindings
import tech.orbitweb.orbitx.libraries.core.meta.BuildMeta
import tech.orbitweb.orbitx.libraries.designsystem.theme.ElementThemeApp
import tech.orbitweb.orbitx.libraries.featureflag.api.FeatureFlagService
import tech.orbitweb.orbitx.libraries.preferences.api.store.AppPreferencesStore

class PinUnlockActivity : AppCompatActivity() {
    internal companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, PinUnlockActivity::class.java)
        }
    }

    @Inject lateinit var presenterFactory: PinUnlockPresenter.Factory
    @Inject lateinit var lockScreenService: LockScreenService
    @Inject lateinit var appPreferencesStore: AppPreferencesStore
    @Inject lateinit var featureFlagService: FeatureFlagService
    @Inject lateinit var enterpriseService: EnterpriseService
    @Inject lateinit var buildMeta: BuildMeta

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        bindings<PinUnlockBindings>().inject(this)
        val presenter = presenterFactory.create(forDeviceUnlock = false)
        setContent {
            val colors by remember {
                enterpriseService.semanticColorsFlow(sessionId = null)
            }.collectAsState(SemanticColorsLightDark.default)
            ElementThemeApp(
                appPreferencesStore = appPreferencesStore,
                featureFlagService = featureFlagService,
                compoundLight = colors.light,
                compoundDark = colors.dark,
                buildMeta = buildMeta,
            ) {
                val state = presenter.present()
                PinUnlockView(
                    state = state,
                    isInAppUnlock = false,
                    onCancel = {
                        // Should not happen
                    },
                )
            }
        }
        lifecycleScope.launch {
            lockScreenService.lockState.collect { state ->
                if (state == LockScreenLockState.Unlocked) {
                    finish()
                }
            }
        }
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                moveTaskToBack(true)
            }
        }
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }
}
