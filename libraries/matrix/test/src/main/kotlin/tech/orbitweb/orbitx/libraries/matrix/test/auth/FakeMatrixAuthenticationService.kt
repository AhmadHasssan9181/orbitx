/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.test.auth

import tech.orbitweb.orbitx.libraries.matrix.api.MatrixClient
import tech.orbitweb.orbitx.libraries.matrix.api.auth.ElementClassicSession
import tech.orbitweb.orbitx.libraries.matrix.api.auth.MatrixAuthenticationService
import tech.orbitweb.orbitx.libraries.matrix.api.auth.MatrixHomeServerDetails
import tech.orbitweb.orbitx.libraries.matrix.api.auth.OAuthDetails
import tech.orbitweb.orbitx.libraries.matrix.api.auth.OAuthPrompt
import tech.orbitweb.orbitx.libraries.matrix.api.auth.external.ExternalSession
import tech.orbitweb.orbitx.libraries.matrix.api.auth.qrlogin.MatrixQrCodeLoginData
import tech.orbitweb.orbitx.libraries.matrix.api.auth.qrlogin.QrCodeLoginStep
import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.libraries.matrix.test.A_SESSION_ID
import tech.orbitweb.orbitx.libraries.matrix.test.A_USER_ID
import tech.orbitweb.orbitx.libraries.matrix.test.FakeMatrixClient
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaRecorder
import tech.orbitweb.orbitx.tests.testutils.simulateLongTask

val AN_OAUTH_DATA = OAuthDetails(url = "a-url")

class FakeMatrixAuthenticationService(
    var matrixClientResult: ((SessionId) -> Result<MatrixClient>)? = null,
    var loginWithQrCodeResult: (qrCodeData: MatrixQrCodeLoginData, progress: (QrCodeLoginStep) -> Unit) -> Result<SessionId> =
        lambdaRecorder<MatrixQrCodeLoginData, (QrCodeLoginStep) -> Unit, Result<SessionId>> { _, _ -> Result.success(A_SESSION_ID) },
    private val importCreatedSessionLambda: (ExternalSession) -> Result<SessionId> = { lambdaError() },
    private val setHomeserverResult: (String) -> Result<MatrixHomeServerDetails> = { lambdaError() },
    private val setElementClassicSessionResult: (ElementClassicSession?) -> Unit = { lambdaError() },
    private val doSecretsContainBackupKeyResult: (UserId, String, String) -> Boolean = { _, _, _ -> lambdaError() },
) : MatrixAuthenticationService {
    private var oAuthError: Throwable? = null
    private var oAuthCancelError: Throwable? = null
    private var loginError: Throwable? = null
    private var matrixClient: MatrixClient? = null
    private var onAuthenticationListener: ((MatrixClient) -> Unit)? = null

    override suspend fun restoreSession(sessionId: SessionId): Result<MatrixClient> {
        matrixClientResult?.let {
            return it.invoke(sessionId)
        }
        return if (matrixClient != null) {
            onAuthenticationListener?.invoke(matrixClient!!)
            Result.success(matrixClient!!)
        } else {
            Result.failure(IllegalStateException())
        }
    }

    override suspend fun setHomeserver(homeserver: String): Result<MatrixHomeServerDetails> = simulateLongTask {
        setHomeserverResult(homeserver)
    }

    override suspend fun login(username: String, password: String): Result<SessionId> = simulateLongTask {
        loginError?.let { Result.failure(it) } ?: run {
            onAuthenticationListener?.invoke(matrixClient ?: FakeMatrixClient())
            Result.success(A_USER_ID)
        }
    }

    override suspend fun importCreatedSession(externalSession: ExternalSession): Result<SessionId> = simulateLongTask {
        return importCreatedSessionLambda(externalSession)
    }

    override suspend fun getOAuthUrl(
        prompt: OAuthPrompt,
        loginHint: String?,
    ): Result<OAuthDetails> = simulateLongTask {
        oAuthError?.let { Result.failure(it) } ?: Result.success(AN_OAUTH_DATA)
    }

    override suspend fun cancelOAuthLogin(): Result<Unit> {
        return oAuthCancelError?.let { Result.failure(it) } ?: Result.success(Unit)
    }

    override suspend fun loginWithOAuth(callbackUrl: String): Result<SessionId> = simulateLongTask {
        loginError?.let { Result.failure(it) } ?: run {
            onAuthenticationListener?.invoke(matrixClient ?: FakeMatrixClient())
            Result.success(A_USER_ID)
        }
    }

    override suspend fun loginWithQrCode(qrCodeData: MatrixQrCodeLoginData, progress: (QrCodeLoginStep) -> Unit): Result<SessionId> = simulateLongTask {
        onAuthenticationListener?.invoke(matrixClient ?: FakeMatrixClient())
        loginWithQrCodeResult(qrCodeData, progress)
    }

    override fun listenToNewMatrixClients(lambda: (MatrixClient) -> Unit) {
        onAuthenticationListener = lambda
    }

    fun givenOAuthError(throwable: Throwable?) {
        oAuthError = throwable
    }

    fun givenOAuthCancelError(throwable: Throwable?) {
        oAuthCancelError = throwable
    }

    fun givenLoginError(throwable: Throwable?) {
        loginError = throwable
    }

    fun givenMatrixClient(matrixClient: MatrixClient) {
        this.matrixClient = matrixClient
    }

    override fun setElementClassicSession(session: ElementClassicSession?) {
        setElementClassicSessionResult(session)
    }

    override fun doSecretsContainBackupKey(userId: UserId, secrets: String, backupInfo: String): Boolean {
        return doSecretsContainBackupKeyResult(userId, secrets, backupInfo)
    }
}
