/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.lockscreen.impl

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import kotlin.time.Duration
import tech.orbitweb.orbitx.appconfig.LockScreenConfig as AppConfigLockScreenConfig

data class LockScreenConfig(
    val isPinMandatory: Boolean,
    val forbiddenPinCodes: Set<String>,
    val pinSize: Int,
    val maxPinCodeAttemptsBeforeLogout: Int,
    val gracePeriod: Duration,
    val isStrongBiometricsEnabled: Boolean,
    val isWeakBiometricsEnabled: Boolean,
)

@ContributesTo(AppScope::class)
@BindingContainer
object LockScreenConfigModule {
    @Provides
    fun providesLockScreenConfig(): LockScreenConfig = LockScreenConfig(
        isPinMandatory = AppConfigLockScreenConfig.IS_PIN_MANDATORY,
        forbiddenPinCodes = AppConfigLockScreenConfig.FORBIDDEN_PIN_CODES,
        pinSize = AppConfigLockScreenConfig.PIN_SIZE,
        maxPinCodeAttemptsBeforeLogout = AppConfigLockScreenConfig.MAX_PIN_CODE_ATTEMPTS_BEFORE_LOGOUT,
        gracePeriod = AppConfigLockScreenConfig.GRACE_PERIOD,
        isStrongBiometricsEnabled = AppConfigLockScreenConfig.IS_STRONG_BIOMETRICS_ENABLED,
        isWeakBiometricsEnabled = AppConfigLockScreenConfig.IS_WEAK_BIOMETRICS_ENABLED,
    )
}
