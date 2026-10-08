/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

@file:OptIn(ExperimentalTestApi::class)

package tech.orbitweb.orbitx.tests.testutils

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.AndroidComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import org.junit.Assert.assertFalse
import tech.orbitweb.orbitx.libraries.designsystem.utils.LocalUiTestMode
import kotlin.coroutines.CoroutineContext

object RobolectricDispatcherCleaner {
    // HACK: Workaround for https://github.com/robolectric/robolectric/issues/7055#issuecomment-1551119229
    fun clearAndroidUiDispatcher(pkg: String = "androidx.compose.ui.platform") {
        val clazz = javaClass.classLoader!!.loadClass("$pkg.AndroidUiDispatcher")
        val combinedContextClass = javaClass.classLoader!!.loadClass("kotlin.coroutines.CombinedContext")
        val companionClazz = clazz.getDeclaredField("Companion").get(clazz)
        val combinedContext = companionClazz.javaClass.getDeclaredMethod("getMain")
            .invoke(companionClazz) as CoroutineContext
        val androidUiDispatcher = combinedContextClass.getDeclaredField("element")
            .apply { isAccessible = true }
            .get(combinedContext)
            .let { clazz.cast(it) }
        var scheduledFrameDispatch = clazz.getDeclaredField("scheduledFrameDispatch")
            .apply { isAccessible = true }
            .getBoolean(androidUiDispatcher)
        var scheduledTrampolineDispatch = clazz.getDeclaredField("scheduledTrampolineDispatch")
            .apply { isAccessible = true }
            .getBoolean(androidUiDispatcher)
        val dispatchCallback = clazz.getDeclaredField("dispatchCallback")
            .apply { isAccessible = true }
            .get(androidUiDispatcher) as Runnable
        if (scheduledFrameDispatch || scheduledTrampolineDispatch) {
            dispatchCallback.run()
            scheduledFrameDispatch = clazz.getDeclaredField("scheduledFrameDispatch")
                .apply { isAccessible = true }
                .getBoolean(androidUiDispatcher)
            scheduledTrampolineDispatch = clazz.getDeclaredField("scheduledTrampolineDispatch")
                .apply { isAccessible = true }
                .getBoolean(androidUiDispatcher)
        }
        assertFalse(scheduledFrameDispatch)
        assertFalse(scheduledTrampolineDispatch)
    }
}

fun AndroidComposeUiTest<ComponentActivity>.setSafeContent(
    clearAndroidUiDispatcher: Boolean = false,
    content: @Composable () -> Unit,
) {
    if (clearAndroidUiDispatcher) {
        RobolectricDispatcherCleaner.clearAndroidUiDispatcher()
    }
    setContent {
        CompositionLocalProvider(LocalUiTestMode provides true) {
            content()
        }
    }
}
