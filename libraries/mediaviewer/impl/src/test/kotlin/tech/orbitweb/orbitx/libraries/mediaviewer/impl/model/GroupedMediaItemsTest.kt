/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.mediaviewer.impl.model

import com.google.common.truth.Truth.assertThat
import kotlinx.collections.immutable.persistentListOf
import org.junit.Test
import tech.orbitweb.orbitx.libraries.matrix.test.AN_EVENT_ID
import tech.orbitweb.orbitx.libraries.matrix.test.AN_EVENT_ID_2
import tech.orbitweb.orbitx.libraries.matrix.test.AN_EVENT_ID_3

class GroupedMediaItemsTest {
    @Test
    fun `hasEvent returns the expected value`() {
        val sut = GroupedMediaItems(
            imageAndVideoItems = persistentListOf(
                aMediaItemImage(eventId = AN_EVENT_ID),
            ),
            fileItems = persistentListOf(
                aMediaItemAudio(eventId = AN_EVENT_ID_2),
            ),
        )
        assertThat(sut.hasEvent(AN_EVENT_ID)).isTrue()
        assertThat(sut.hasEvent(AN_EVENT_ID_2)).isTrue()
        assertThat(sut.hasEvent(AN_EVENT_ID_3)).isFalse()
    }
}
