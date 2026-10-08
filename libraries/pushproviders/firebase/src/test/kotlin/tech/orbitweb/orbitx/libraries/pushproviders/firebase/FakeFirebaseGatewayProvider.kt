/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.pushproviders.firebase

const val A_FIREBASE_GATEWAY = "aGateway"

class FakeFirebaseGatewayProvider(
    private val firebaseGatewayResult: () -> String = { A_FIREBASE_GATEWAY }
) : FirebaseGatewayProvider {
    override fun getFirebaseGateway() = firebaseGatewayResult()
}
