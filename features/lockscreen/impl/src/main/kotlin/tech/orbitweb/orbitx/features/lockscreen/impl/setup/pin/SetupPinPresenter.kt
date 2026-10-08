/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.lockscreen.impl.setup.pin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.delay
import tech.orbitweb.orbitx.features.lockscreen.impl.LockScreenConfig
import tech.orbitweb.orbitx.features.lockscreen.impl.pin.PinCodeManager
import tech.orbitweb.orbitx.features.lockscreen.impl.pin.model.PinEntry
import tech.orbitweb.orbitx.features.lockscreen.impl.setup.pin.validation.PinValidator
import tech.orbitweb.orbitx.features.lockscreen.impl.setup.pin.validation.SetupPinFailure
import tech.orbitweb.orbitx.libraries.architecture.Presenter
import tech.orbitweb.orbitx.libraries.core.meta.BuildMeta

/**
 * Some time for the ui to refresh before showing confirmation step.
 */
private const val DELAY_BEFORE_CONFIRMATION_STEP_IN_MILLIS = 100L

@Inject
class SetupPinPresenter(
    private val lockScreenConfig: LockScreenConfig,
    private val pinValidator: PinValidator,
    private val buildMeta: BuildMeta,
    private val pinCodeManager: PinCodeManager,
) : Presenter<SetupPinState> {
    @Composable
    override fun present(): SetupPinState {
        var choosePinEntry by remember {
            mutableStateOf(PinEntry.createEmpty(lockScreenConfig.pinSize))
        }
        var confirmPinEntry by remember {
            mutableStateOf(PinEntry.createEmpty(lockScreenConfig.pinSize))
        }
        var isConfirmationStep by remember {
            mutableStateOf(false)
        }
        var setupPinFailure by remember {
            mutableStateOf<SetupPinFailure?>(null)
        }
        LaunchedEffect(choosePinEntry) {
            if (choosePinEntry.isComplete()) {
                when (val pinValidationResult = pinValidator.isPinValid(choosePinEntry)) {
                    is PinValidator.Result.Invalid -> {
                        setupPinFailure = pinValidationResult.failure
                    }
                    PinValidator.Result.Valid -> {
                        delay(DELAY_BEFORE_CONFIRMATION_STEP_IN_MILLIS)
                        isConfirmationStep = true
                    }
                }
            }
        }

        LaunchedEffect(confirmPinEntry) {
            if (confirmPinEntry.isComplete()) {
                if (confirmPinEntry == choosePinEntry) {
                    pinCodeManager.createPinCode(confirmPinEntry.toText())
                } else {
                    setupPinFailure = SetupPinFailure.PinsDoNotMatch
                }
            }
        }

        fun handleEvent(event: SetupPinEvent) {
            when (event) {
                is SetupPinEvent.OnPinEntryChanged -> {
                    // Use the fromConfirmationStep flag from ui to avoid race condition.
                    if (event.fromConfirmationStep) {
                        confirmPinEntry = confirmPinEntry.fillWith(event.entryAsText)
                    } else {
                        choosePinEntry = choosePinEntry.fillWith(event.entryAsText)
                    }
                }
                SetupPinEvent.ClearFailure -> {
                    when (setupPinFailure) {
                        is SetupPinFailure.PinsDoNotMatch -> {
                            choosePinEntry = choosePinEntry.clear()
                            confirmPinEntry = confirmPinEntry.clear()
                        }
                        is SetupPinFailure.ForbiddenPin -> {
                            choosePinEntry = choosePinEntry.clear()
                        }
                        null -> Unit
                    }
                    isConfirmationStep = false
                    setupPinFailure = null
                }
            }
        }

        return SetupPinState(
            choosePinEntry = choosePinEntry,
            confirmPinEntry = confirmPinEntry,
            isConfirmationStep = isConfirmationStep,
            setupPinFailure = setupPinFailure,
            appName = buildMeta.applicationName,
            eventSink = ::handleEvent,
        )
    }
}
