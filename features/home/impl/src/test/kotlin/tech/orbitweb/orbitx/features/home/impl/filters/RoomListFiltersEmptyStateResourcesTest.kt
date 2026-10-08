/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.home.impl.filters

import com.google.common.truth.Truth.assertThat
import tech.orbitweb.orbitx.features.home.impl.R
import org.junit.Test

class RoomListFiltersEmptyStateResourcesTest {
    @Test
    fun `fromSelectedFilters should return null when selectedFilters is empty`() {
        val selectedFilters = emptyList<RoomListFilter>()
        val result = RoomListFiltersEmptyStateResources.fromSelectedFilters(selectedFilters, isSpaceFilterSelected = false)
        assertThat(result).isNull()
    }

    @Test
    fun `fromSelectedFilters should return exact RoomListFiltersEmptyStateResources when selectedFilters has only unread filter`() {
        val selectedFilters = listOf(RoomListFilter.Unread)
        val result = RoomListFiltersEmptyStateResources.fromSelectedFilters(selectedFilters, isSpaceFilterSelected = false)
        assertThat(result).isNotNull()
        assertThat(result?.title).isEqualTo(R.string.screen_roomlist_filter_unreads_empty_state_title)
        assertThat(result?.subtitle).isEqualTo(R.string.screen_roomlist_filter_mixed_empty_state_subtitle)
    }

    @Test
    fun `fromSelectedFilters should return exact RoomListFiltersEmptyStateResources when selectedFilters has only people filter`() {
        val selectedFilters = listOf(RoomListFilter.People)
        val result = RoomListFiltersEmptyStateResources.fromSelectedFilters(selectedFilters, isSpaceFilterSelected = false)
        assertThat(result).isNotNull()
        assertThat(result?.title).isEqualTo(R.string.screen_roomlist_filter_people_empty_state_title)
        assertThat(result?.subtitle).isEqualTo(R.string.screen_roomlist_filter_mixed_empty_state_subtitle)
    }

    @Test
    fun `fromSelectedFilters should return exact RoomListFiltersEmptyStateResources when selectedFilters has only rooms filter`() {
        val selectedFilters = listOf(RoomListFilter.Rooms)
        val result = RoomListFiltersEmptyStateResources.fromSelectedFilters(selectedFilters, isSpaceFilterSelected = false)
        assertThat(result).isNotNull()
        assertThat(result?.title).isEqualTo(R.string.screen_roomlist_filter_rooms_empty_state_title)
        assertThat(result?.subtitle).isEqualTo(R.string.screen_roomlist_filter_mixed_empty_state_subtitle)
    }

    @Test
    fun `fromSelectedFilters should return exact RoomListFiltersEmptyStateResources when selectedFilters has only favourites filter`() {
        val selectedFilters = listOf(RoomListFilter.Favourites)
        val result = RoomListFiltersEmptyStateResources.fromSelectedFilters(selectedFilters, isSpaceFilterSelected = false)
        assertThat(result).isNotNull()
        assertThat(result?.title).isEqualTo(R.string.screen_roomlist_filter_favourites_empty_state_title)
        assertThat(result?.subtitle).isEqualTo(R.string.screen_roomlist_filter_favourites_empty_state_subtitle)
    }

    @Test
    fun `fromSelectedFilters should return exact RoomListFiltersEmptyStateResources when selectedFilters has only invites filter`() {
        val selectedFilters = listOf(RoomListFilter.Invites)
        val result = RoomListFiltersEmptyStateResources.fromSelectedFilters(selectedFilters, isSpaceFilterSelected = false)
        assertThat(result).isNotNull()
        assertThat(result?.title).isEqualTo(R.string.screen_roomlist_filter_invites_empty_state_title)
        assertThat(result?.subtitle).isEqualTo(R.string.screen_roomlist_filter_mixed_empty_state_subtitle)
    }

    @Test
    fun `fromSelectedFilters should return exact RoomListFiltersEmptyStateResources when selectedFilters has multiple filters`() {
        val selectedFilters = listOf(RoomListFilter.Unread, RoomListFilter.People, RoomListFilter.Rooms, RoomListFilter.Favourites)
        val result = RoomListFiltersEmptyStateResources.fromSelectedFilters(selectedFilters, isSpaceFilterSelected = false)
        assertThat(result).isNotNull()
        assertThat(result?.title).isEqualTo(R.string.screen_roomlist_filter_mixed_empty_state_title)
        assertThat(result?.subtitle).isEqualTo(R.string.screen_roomlist_filter_mixed_empty_state_subtitle)
    }

    @Test
    fun `fromSelectedFilters should return exact RoomListFiltersEmptyStateResources when isSpaceFilterSelected is true`() {
        val result = RoomListFiltersEmptyStateResources.fromSelectedFilters(emptyList(), isSpaceFilterSelected = true)
        assertThat(result).isNotNull()
        assertThat(result?.title).isEqualTo(R.string.screen_roomlist_filter_mixed_empty_state_title)
        assertThat(result?.subtitle).isEqualTo(R.string.screen_roomlist_filter_mixed_empty_state_subtitle)
    }
}
