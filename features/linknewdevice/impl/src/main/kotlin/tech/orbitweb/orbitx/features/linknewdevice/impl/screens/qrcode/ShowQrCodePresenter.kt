/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.linknewdevice.impl.screens.qrcode

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import tech.orbitweb.orbitx.features.linknewdevice.impl.LinkNewMobileHandler
import tech.orbitweb.orbitx.libraries.architecture.AsyncData
import tech.orbitweb.orbitx.libraries.architecture.Presenter
import tech.orbitweb.orbitx.libraries.core.log.logger.LoggerTag
import tech.orbitweb.orbitx.libraries.matrix.api.linknewdevice.LinkMobileStep
import tech.orbitweb.orbitx.libraries.matrix.api.logs.LoggerTags
import timber.log.Timber

private val tag = LoggerTag("ShowQrCodePresenter", LoggerTags.linkNewDevice)

@AssistedInject
class ShowQrCodePresenter(
    @Assisted private val initialData: String,
    private val linkNewMobileHandler: LinkNewMobileHandler,
) : Presenter<ShowQrCodeState> {
    @AssistedFactory
    interface Factory {
        fun create(initialData: String): ShowQrCodePresenter
    }

    private var loadingJob: Job? = null

    @Composable
    override fun present(): ShowQrCodeState {
        var qrCodeRotationCounter by remember { mutableIntStateOf(MAX_QR_CODE_ROTATION) }
        val state by produceState(
            initialValue = ShowQrCodeState(
                data = AsyncData.Success(initialData),
            )
        ) {
            linkNewMobileHandler.stepFlow.collect { step ->
                when (step) {
                    is LinkMobileStep.QrReady -> {
                        loadingJob?.cancel()
                        value = ShowQrCodeState(
                            data = AsyncData.Success(step.data),
                        )
                    }
                    is LinkMobileStep.QrRotating -> {
                        if (qrCodeRotationCounter-- > 0) {
                            Timber.tag(tag.value).d("Rotating QrCode")
                            linkNewMobileHandler.rotateQrCode()
                            // Ensure that outdated data is not rendered too long while rotating QR code
                            loadingJob = launch {
                                delay(1000)
                                value = ShowQrCodeState(
                                    data = AsyncData.Loading(),
                                )
                            }
                        } else {
                            Timber.tag(tag.value).w("Max QR code rotation reached, not rotating anymore")
                            linkNewMobileHandler.onTooManyRotation()
                        }
                    }
                    else -> Unit
                }
            }
        }

        return state
    }

    companion object {
        const val MAX_QR_CODE_ROTATION = 10
    }
}
