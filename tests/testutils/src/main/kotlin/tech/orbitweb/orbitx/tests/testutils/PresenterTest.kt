/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.tests.testutils

import app.cash.molecule.RecompositionMode
import app.cash.molecule.moleculeFlow
import app.cash.turbine.TurbineTestContext
import app.cash.turbine.test
import tech.orbitweb.orbitx.libraries.architecture.Presenter
import org.junit.Assert.fail
import kotlin.time.Duration

suspend fun <State> Presenter<State>.test(
    timeout: Duration? = null,
    name: String? = null,
    validate: suspend TurbineTestContext<State>.() -> Unit,
) {
    try {
        moleculeFlow(RecompositionMode.Immediate) {
            present()
        }.test(timeout, name, validate)
    } catch (t: Throwable) {
        if (t::class.simpleName == "KotlinReflectionInternalError") {
            // Give a more explicit error to the developer
            fail("""
                It looks like you have an unconsumed event in your test.
                    If you get this error, it means that your test is missing to consume one or several events.
                    You can fix by consuming and check the event with `awaitItem()`, or you can also invoke
                    `cancelAndIgnoreRemainingEvents()`.
                """.trimIndent())
        }
        throw t
    }
}
