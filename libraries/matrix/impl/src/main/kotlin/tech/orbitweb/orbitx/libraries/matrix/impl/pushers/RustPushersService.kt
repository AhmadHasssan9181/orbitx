/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.pushers

import tech.orbitweb.orbitx.libraries.core.coroutine.CoroutineDispatchers
import tech.orbitweb.orbitx.libraries.core.extensions.mapFailure
import tech.orbitweb.orbitx.libraries.core.extensions.runCatchingExceptions
import tech.orbitweb.orbitx.libraries.matrix.api.pusher.PushersService
import tech.orbitweb.orbitx.libraries.matrix.api.pusher.SetHttpPusherData
import tech.orbitweb.orbitx.libraries.matrix.api.pusher.UnsetHttpPusherData
import tech.orbitweb.orbitx.libraries.matrix.impl.exception.mapClientException
import kotlinx.coroutines.withContext
import org.matrix.rustcomponents.sdk.Client
import org.matrix.rustcomponents.sdk.HttpPusherData
import org.matrix.rustcomponents.sdk.PushFormat
import org.matrix.rustcomponents.sdk.PusherIdentifiers
import org.matrix.rustcomponents.sdk.PusherKind

class RustPushersService(
    private val client: Client,
    private val dispatchers: CoroutineDispatchers
) : PushersService {
    override suspend fun setHttpPusher(setHttpPusherData: SetHttpPusherData): Result<Unit> {
        return withContext(dispatchers.io) {
            runCatchingExceptions {
                client.setPusher(
                    identifiers = PusherIdentifiers(
                        pushkey = setHttpPusherData.pushKey,
                        appId = setHttpPusherData.appId
                    ),
                    kind = PusherKind.Http(
                        data = HttpPusherData(
                            url = setHttpPusherData.url,
                            format = PushFormat.EVENT_ID_ONLY,
                            defaultPayload = setHttpPusherData.defaultPayload
                        )
                    ),
                    appDisplayName = setHttpPusherData.appDisplayName,
                    deviceDisplayName = setHttpPusherData.deviceDisplayName,
                    profileTag = setHttpPusherData.profileTag,
                    lang = setHttpPusherData.lang,
                    append = setHttpPusherData.append,
                )
            }
                .mapFailure { it.mapClientException() }
        }
    }

    override suspend fun unsetHttpPusher(unsetHttpPusherData: UnsetHttpPusherData): Result<Unit> {
        return withContext(dispatchers.io) {
            runCatchingExceptions {
                client.deletePusher(
                    identifiers = PusherIdentifiers(
                        pushkey = unsetHttpPusherData.pushKey,
                        appId = unsetHttpPusherData.appId
                    ),
                )
            }
        }
    }
}
