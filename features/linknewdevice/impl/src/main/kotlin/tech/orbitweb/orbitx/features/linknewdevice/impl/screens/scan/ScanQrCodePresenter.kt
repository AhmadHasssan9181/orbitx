/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.linknewdevice.impl.screens.scan

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.launch
import tech.orbitweb.orbitx.features.linknewdevice.impl.LinkNewDesktopHandler
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.architecture.Presenter
import tech.orbitweb.orbitx.libraries.matrix.api.linknewdevice.LinkDesktopStep

@Inject
class ScanQrCodePresenter(
    private val linkNewDesktopHandler: LinkNewDesktopHandler,
) : Presenter<ScanQrCodeState> {
    @Composable
    override fun present(): ScanQrCodeState {
        val coroutineScope = rememberCoroutineScope()
        var scanAction: AsyncAction<Unit> by remember { mutableStateOf(AsyncAction.Loading) }

        // Observe the flow to react on LinkDesktopStep.InvalidQrCode
        val linkDesktopStep by linkNewDesktopHandler.stepFlow.collectAsState()

        LaunchedEffect(Unit) {
            linkNewDesktopHandler.createNewHandler()
        }

        LaunchedEffect(linkDesktopStep) {
            when (val step = linkDesktopStep) {
                is LinkDesktopStep.InvalidQrCode -> {
                    scanAction = AsyncAction.Failure(Exception(step.error))
                }
                else -> Unit
            }
        }

        fun handleEvent(event: ScanQrCodeEvent) {
            when (event) {
                ScanQrCodeEvent.TryAgain -> {
                    scanAction = AsyncAction.Loading
                }
                is ScanQrCodeEvent.QrCodeScanned -> coroutineScope.launch {
                    // In this case the scanning will stop and a loader will be shown
                    scanAction = AsyncAction.Success(Unit)
                    try {
                        linkNewDesktopHandler.onScannedCode(event.data)
                    } catch (e: Exception) {
                        // Should not happen as errors are handled through the LinkDesktopStep flow
                        scanAction = AsyncAction.Failure(e)
                    }
                }
            }
        }

        return ScanQrCodeState(
            scanAction = scanAction,
            eventSink = ::handleEvent,
        )
    }
}
