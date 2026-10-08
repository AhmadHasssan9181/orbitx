/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.login.impl.screens.qrcode.scan

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Test
import tech.orbitweb.orbitx.features.enterprise.api.EnterpriseService
import tech.orbitweb.orbitx.features.enterprise.test.FakeEnterpriseService
import tech.orbitweb.orbitx.features.login.impl.accesscontrol.DefaultAccountProviderAccessControl
import tech.orbitweb.orbitx.features.login.impl.changeserver.AccountProviderAccessException
import tech.orbitweb.orbitx.features.login.impl.qrcode.FakeQrCodeLoginManager
import tech.orbitweb.orbitx.features.wellknown.test.FakeWellknownRetriever
import tech.orbitweb.orbitx.libraries.core.coroutine.CoroutineDispatchers
import tech.orbitweb.orbitx.libraries.matrix.api.auth.qrlogin.QrCodeLoginStep
import tech.orbitweb.orbitx.libraries.matrix.api.auth.qrlogin.QrLoginException
import tech.orbitweb.orbitx.libraries.matrix.test.auth.qrlogin.FakeMatrixQrCodeLoginData
import tech.orbitweb.orbitx.libraries.matrix.test.auth.qrlogin.FakeMatrixQrCodeLoginDataFactory
import tech.orbitweb.orbitx.libraries.wellknown.api.WellknownRetriever
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaRecorder
import tech.orbitweb.orbitx.tests.testutils.test
import tech.orbitweb.orbitx.tests.testutils.testCoroutineDispatchers

class QrCodeScanPresenterTest {
    @Test
    fun `present - initial state`() = runTest {
        val presenter = createQrCodeScanPresenter()
        presenter.test {
            awaitItem().run {
                assertThat(isScanning).isTrue()
                assertThat(authenticationAction.isUninitialized()).isTrue()
            }
        }
    }

    @Test
    fun `present - scanned QR code successfully`() = runTest {
        val qrCodeLoginDataFactory = FakeMatrixQrCodeLoginDataFactory(
            parseQrCodeLoginDataResult = {
                Result.success(
                    FakeMatrixQrCodeLoginData(
                        serverNameResult = { "example.com" }
                    )
                )
            }
        )
        val presenter = createQrCodeScanPresenter(
            qrCodeLoginDataFactory = qrCodeLoginDataFactory,
            enterpriseService = FakeEnterpriseService(
                isAllowedToConnectToHomeserverResult = { true },
            )
        )
        presenter.test {
            val initialState = awaitItem()
            initialState.eventSink(QrCodeScanEvents.QrCodeScanned(byteArrayOf()))
            assertThat(awaitItem().isScanning).isFalse()
            assertThat(awaitItem().authenticationAction.isLoading()).isTrue()
            assertThat(awaitItem().authenticationAction.isSuccess()).isTrue()
        }
    }

    @Test
    fun `present - scanned QR code successfully, but homeserver not allowed`() = runTest {
        val qrCodeLoginDataFactory = FakeMatrixQrCodeLoginDataFactory(
            parseQrCodeLoginDataResult = {
                Result.success(
                    FakeMatrixQrCodeLoginData(
                        serverNameResult = { "example.com" }
                    )
                )
            }
        )
        val presenter = createQrCodeScanPresenter(
            qrCodeLoginDataFactory = qrCodeLoginDataFactory,
            enterpriseService = FakeEnterpriseService(
                isAllowedToConnectToHomeserverResult = { false },
                defaultHomeserverListResult = { listOf("element.io") },
            )
        )
        presenter.test {
            val initialState = awaitItem()
            initialState.eventSink(QrCodeScanEvents.QrCodeScanned(byteArrayOf()))
            assertThat(awaitItem().isScanning).isFalse()
            assertThat(awaitItem().authenticationAction.isLoading()).isTrue()
            awaitItem().also { state ->
                assertThat(
                    (state.authenticationAction
                        .errorOrNull() as AccountProviderAccessException.UnauthorizedAccountProviderException).unauthorisedAccountProviderTitle
                )
                    .isEqualTo("example.com")
                assertThat(
                    (state.authenticationAction
                        .errorOrNull() as AccountProviderAccessException.UnauthorizedAccountProviderException).authorisedAccountProviderTitles
                )
                    .containsExactly("element.io")
            }
        }
    }

    @Test
    fun `present - scanned QR code failed and can be retried`() = runTest {
        val qrCodeLoginDataFactory = FakeMatrixQrCodeLoginDataFactory(
            parseQrCodeLoginDataResult = { Result.failure(Exception("Failed to parse QR code")) }
        )
        val presenter = createQrCodeScanPresenter(qrCodeLoginDataFactory = qrCodeLoginDataFactory)
        presenter.test {
            val initialState = awaitItem()
            initialState.eventSink(QrCodeScanEvents.QrCodeScanned(byteArrayOf()))
            assertThat(awaitItem().isScanning).isFalse()
            assertThat(awaitItem().authenticationAction.isLoading()).isTrue()

            val errorState = awaitItem()
            assertThat(errorState.authenticationAction.isFailure()).isTrue()

            errorState.eventSink(QrCodeScanEvents.TryAgain)
            assertThat(awaitItem().isScanning).isTrue()
            assertThat(awaitItem().authenticationAction.isUninitialized()).isTrue()
        }
    }

    @Test
    fun `present - login failed with so we display the error and recover from it`() = runTest {
        val qrCodeLoginDataFactory = FakeMatrixQrCodeLoginDataFactory()
        val qrCodeLoginManager = FakeQrCodeLoginManager()
        val resetAction = lambdaRecorder<Unit> {
            qrCodeLoginManager.currentLoginStep.value = QrCodeLoginStep.Uninitialized
        }
        qrCodeLoginManager.resetAction = resetAction
        val presenter = createQrCodeScanPresenter(qrCodeLoginDataFactory = qrCodeLoginDataFactory, qrCodeLoginManager = qrCodeLoginManager)
        presenter.test {
            // Skip initial item
            skipItems(1)

            qrCodeLoginManager.currentLoginStep.value = QrCodeLoginStep.Failed(QrLoginException.OtherDeviceNotSignedIn)

            val errorState = awaitItem()
            // The state for this screen is failure
            assertThat(errorState.authenticationAction.isFailure()).isTrue()
            // However, the QrCodeLoginManager is reset
            resetAction.assertions().isCalledOnce()
            assertThat(qrCodeLoginManager.currentLoginStep.value).isEqualTo(QrCodeLoginStep.Uninitialized)
        }
    }

    private fun TestScope.createQrCodeScanPresenter(
        qrCodeLoginDataFactory: FakeMatrixQrCodeLoginDataFactory = FakeMatrixQrCodeLoginDataFactory(),
        coroutineDispatchers: CoroutineDispatchers = testCoroutineDispatchers(),
        qrCodeLoginManager: FakeQrCodeLoginManager = FakeQrCodeLoginManager(),
        enterpriseService: EnterpriseService = FakeEnterpriseService(),
        wellknownRetriever: WellknownRetriever = FakeWellknownRetriever(),
    ) = QrCodeScanPresenter(
        qrCodeLoginDataFactory = qrCodeLoginDataFactory,
        qrCodeLoginManager = qrCodeLoginManager,
        coroutineDispatchers = coroutineDispatchers,
        defaultAccountProviderAccessControl = DefaultAccountProviderAccessControl(
            enterpriseService = enterpriseService,
            wellknownRetriever = wellknownRetriever,
        ),
    )
}
