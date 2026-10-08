/*
 * Copyright (c) 2026 Orbit Team / Orbit Commercial Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.home.impl.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import tech.orbitweb.orbitx.appconfig.RoomListConfig
import tech.orbitweb.orbitx.compound.theme.ElementTheme
import tech.orbitweb.orbitx.compound.tokens.generated.CompoundIcons
import tech.orbitweb.orbitx.features.home.impl.HomeNavigationBarItem
import tech.orbitweb.orbitx.features.home.impl.R
import tech.orbitweb.orbitx.features.home.impl.filters.RoomListFiltersState
import tech.orbitweb.orbitx.features.home.impl.filters.RoomListFiltersView
import tech.orbitweb.orbitx.features.home.impl.filters.aRoomListFiltersState
import tech.orbitweb.orbitx.features.home.impl.spacefilters.SpaceFiltersEvent
import tech.orbitweb.orbitx.features.home.impl.spacefilters.SpaceFiltersState
import tech.orbitweb.orbitx.features.home.impl.spacefilters.aSelectedSpaceFiltersState
import tech.orbitweb.orbitx.features.home.impl.spacefilters.anUnselectedSpaceFiltersState
import tech.orbitweb.orbitx.libraries.designsystem.atomic.atoms.RedIndicatorAtom
import tech.orbitweb.orbitx.libraries.designsystem.components.TopAppBarScrollBehaviorLayout
import tech.orbitweb.orbitx.libraries.designsystem.components.avatar.Avatar
import tech.orbitweb.orbitx.libraries.designsystem.components.avatar.AvatarSize
import tech.orbitweb.orbitx.libraries.designsystem.components.avatar.AvatarType
import tech.orbitweb.orbitx.libraries.designsystem.modifiers.backgroundVerticalGradient
import tech.orbitweb.orbitx.libraries.designsystem.preview.ElementPreview
import tech.orbitweb.orbitx.libraries.designsystem.preview.PreviewsDayNight
import tech.orbitweb.orbitx.libraries.designsystem.preview.USER_NAME_ALICE
import tech.orbitweb.orbitx.libraries.designsystem.theme.aliasScreenTitle
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.DropdownMenu
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.DropdownMenuItem
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.Icon
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.IconButton
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.Text
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.TopAppBar
import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId
import tech.orbitweb.orbitx.libraries.matrix.api.user.MatrixUser
import tech.orbitweb.orbitx.libraries.matrix.ui.components.aMatrixUser
import tech.orbitweb.orbitx.libraries.matrix.ui.components.aMatrixUserList
import tech.orbitweb.orbitx.libraries.matrix.ui.model.getAvatarData
import tech.orbitweb.orbitx.libraries.testtags.TestTags
import tech.orbitweb.orbitx.libraries.testtags.testTag
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    selectedNavigationItem: HomeNavigationBarItem,
    currentUserAndNeighbors: ImmutableList<MatrixUser>,
    showAvatarIndicator: Boolean,
    areSearchResultsDisplayed: Boolean,
    onToggleSearch: () -> Unit,
    onMenuActionClick: (RoomListMenuAction) -> Unit,
    onOpenSettings: () -> Unit,
    onAccountSwitch: (SessionId) -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    canReportBug: Boolean,
    displayFilters: Boolean,
    filtersState: RoomListFiltersState,
    spaceFiltersState: SpaceFiltersState,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        TopAppBar(
            modifier = Modifier
                .backgroundVerticalGradient(
                    isVisible = !areSearchResultsDisplayed,
                )
                .statusBarsPadding(),
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent,
                scrolledContainerColor = Color.Transparent,
            ),
            title = {
                val spaceDisplayName = (spaceFiltersState as? SpaceFiltersState.Selected)?.selectedFilter?.spaceRoom?.displayName
                if (spaceDisplayName != null) {
                    Text(
                        modifier = Modifier.semantics { heading() },
                        style = ElementTheme.typography.aliasScreenTitle,
                        text = spaceDisplayName,
                    )
                } else if (selectedNavigationItem == HomeNavigationBarItem.Chats) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Image(
                            painter = painterResource(id = tech.orbitweb.orbitx.libraries.designsystem.R.drawable.orbit_transparent),
                            contentDescription = "Orbit",
                            modifier = Modifier.size(28.dp),
                        )
                        Text(
                            text = "ORBIT",
                            style = ElementTheme.typography.aliasScreenTitle.copy(
                                fontWeight = FontWeight.Black,
                                letterSpacing = 2.sp,
                                brush = Brush.linearGradient(
                                    listOf(
                                        Color(0xFFC4B5FD),
                                        Color(0xFFA78BFA),
                                        Color(0xFF818CF8),
                                        Color(0xFFF472B6)
                                    )
                                )
                            ),
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(
                                    Brush.horizontalGradient(
                                        listOf(Color(0xFF8B5CF6), Color(0xFF6366F1))
                                    )
                                )
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "X",
                                color = Color.White,
                                style = ElementTheme.typography.fontBodySmMedium.copy(
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            )
                        }
                    }
                } else {
                    Text(
                        modifier = Modifier.semantics { heading() },
                        style = ElementTheme.typography.aliasScreenTitle,
                        text = stringResource(selectedNavigationItem.labelRes),
                    )
                }
            },
            navigationIcon = {
                NavigationIcon(
                    currentUserAndNeighbors = currentUserAndNeighbors,
                    showAvatarIndicator = showAvatarIndicator,
                    onAccountSwitch = onAccountSwitch,
                    onClick = onOpenSettings,
                )
            },
            actions = {
                if (selectedNavigationItem == HomeNavigationBarItem.Chats) {
                    RoomListMenuItems(
                        onToggleSearch = onToggleSearch,
                        onMenuActionClick = onMenuActionClick,
                        canReportBug = canReportBug,
                        spaceFiltersState = spaceFiltersState,
                    )
                }
            },
            windowInsets = WindowInsets(left = 4.dp),
        )
        if (displayFilters) {
            TopAppBarScrollBehaviorLayout(scrollBehavior = scrollBehavior) {
                RoomListFiltersView(
                    state = filtersState,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
        }
    }
}

@Composable
private fun RowScope.RoomListMenuItems(
    onToggleSearch: () -> Unit,
    onMenuActionClick: (RoomListMenuAction) -> Unit,
    canReportBug: Boolean,
    spaceFiltersState: SpaceFiltersState,
) {
    IconButton(
        onClick = onToggleSearch,
        modifier = Modifier
            .padding(end = 4.dp)
            .size(38.dp)
            .clip(CircleShape)
            .background(Color(0x18FFFFFF))
    ) {
        Icon(
            imageVector = CompoundIcons.Search(),
            contentDescription = stringResource(CommonStrings.action_search),
            tint = Color.White,
        )
    }
    SpaceFilterButton(spaceFiltersState = spaceFiltersState)
    if (RoomListConfig.HAS_DROP_DOWN_MENU) {
        var showMenu by remember { mutableStateOf(false) }
        IconButton(
            onClick = { showMenu = !showMenu },
            modifier = Modifier
                .padding(end = 8.dp)
                .size(38.dp)
                .clip(CircleShape)
                .background(Color(0x18FFFFFF))
        ) {
            Icon(
                imageVector = CompoundIcons.OverflowVertical(),
                contentDescription = null,
                tint = Color.White,
            )
        }
        DropdownMenu(
            expanded = showMenu,
            onDismissRequest = { showMenu = false }
        ) {
            if (RoomListConfig.SHOW_INVITE_MENU_ITEM) {
                DropdownMenuItem(
                    onClick = {
                        showMenu = false
                        onMenuActionClick(RoomListMenuAction.InviteFriends)
                    },
                    text = { Text(stringResource(id = CommonStrings.action_invite)) },
                    leadingIcon = {
                        Icon(
                            imageVector = CompoundIcons.ShareAndroid(),
                            tint = ElementTheme.colors.iconSecondary,
                            contentDescription = null,
                        )
                    }
                )
            }
            if (RoomListConfig.SHOW_REPORT_PROBLEM_MENU_ITEM && canReportBug) {
                DropdownMenuItem(
                    onClick = {
                        showMenu = false
                        onMenuActionClick(RoomListMenuAction.ReportBug)
                    },
                    text = { Text(stringResource(id = CommonStrings.common_report_a_problem)) },
                    leadingIcon = {
                        Icon(
                            imageVector = CompoundIcons.ChatProblem(),
                            tint = ElementTheme.colors.iconSecondary,
                            contentDescription = null,
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun SpaceFilterButton(
    spaceFiltersState: SpaceFiltersState,
) {
    if (spaceFiltersState == SpaceFiltersState.Disabled) return

    fun onClick() {
        when (spaceFiltersState) {
            is SpaceFiltersState.Unselected -> spaceFiltersState.eventSink(SpaceFiltersEvent.Unselected.ShowFilters)
            is SpaceFiltersState.Selected -> spaceFiltersState.eventSink(SpaceFiltersEvent.Selected.ClearSelection)
            else -> Unit
        }
    }

    val isSelected = spaceFiltersState is SpaceFiltersState.Selected
    IconButton(
        onClick = ::onClick,
        colors = if (isSelected) {
            IconButtonDefaults.iconButtonColors(
                containerColor = ElementTheme.colors.bgActionPrimaryRest,
                contentColor = ElementTheme.colors.iconOnSolidPrimary,
            )
        } else {
            IconButtonDefaults.iconButtonColors()
        },
    ) {
        Icon(
            imageVector = CompoundIcons.Filter(),
            contentDescription = stringResource(R.string.screen_roomlist_your_spaces),
        )
    }
}

@Composable
private fun NavigationIcon(
    currentUserAndNeighbors: ImmutableList<MatrixUser>,
    showAvatarIndicator: Boolean,
    onAccountSwitch: (SessionId) -> Unit,
    onClick: () -> Unit,
) {
    if (currentUserAndNeighbors.size == 1) {
        AccountIcon(
            matrixUser = currentUserAndNeighbors.single(),
            isCurrentAccount = true,
            showAvatarIndicator = showAvatarIndicator,
            onClick = onClick,
        )
    } else {
        // Render a vertical pager
        val pagerState = rememberPagerState(initialPage = 1) { currentUserAndNeighbors.size }
        // Listen to page changes and switch account if needed
        val latestOnAccountSwitch by rememberUpdatedState(onAccountSwitch)
        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.settledPage }.collect { page ->
                latestOnAccountSwitch(SessionId(currentUserAndNeighbors[page].userId.value))
            }
        }
        VerticalPager(
            state = pagerState,
            modifier = Modifier.height(48.dp),
        ) { page ->
            AccountIcon(
                matrixUser = currentUserAndNeighbors[page],
                isCurrentAccount = page == 1,
                showAvatarIndicator = page == 1 && showAvatarIndicator,
                onClick = if (page == 1) {
                    onClick
                } else {
                    {}
                },
            )
        }
    }
}

@Composable
private fun AccountIcon(
    matrixUser: MatrixUser,
    isCurrentAccount: Boolean,
    showAvatarIndicator: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val testTag = if (isCurrentAccount) Modifier.testTag(TestTags.homeScreenSettings) else Modifier
    IconButton(
        modifier = modifier.then(testTag),
        onClick = onClick,
    ) {
        Box {
            val avatarData by remember(matrixUser) {
                derivedStateOf {
                    matrixUser.getAvatarData(size = AvatarSize.CurrentUserTopBar)
                }
            }
            Avatar(
                avatarData = avatarData,
                avatarType = AvatarType.User,
                contentDescription = if (isCurrentAccount) {
                    if (showAvatarIndicator) {
                        stringResource(CommonStrings.a11y_settings_with_required_action)
                    } else {
                        stringResource(CommonStrings.common_settings)
                    }
                } else {
                    null
                },
            )
            if (showAvatarIndicator) {
                RedIndicatorAtom(
                    modifier = Modifier.align(Alignment.TopEnd)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@PreviewsDayNight
@Composable
internal fun HomeTopBarPreview() = ElementPreview {
    HomeTopBar(
        selectedNavigationItem = HomeNavigationBarItem.Chats,
        currentUserAndNeighbors = persistentListOf(aMatrixUser(id = "@id:domain", displayName = USER_NAME_ALICE)),
        showAvatarIndicator = false,
        areSearchResultsDisplayed = false,
        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState()),
        onOpenSettings = {},
        onAccountSwitch = {},
        onToggleSearch = {},
        canReportBug = true,
        displayFilters = true,
        filtersState = aRoomListFiltersState(),
        spaceFiltersState = anUnselectedSpaceFiltersState(),
        onMenuActionClick = {},
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@PreviewsDayNight
@Composable
internal fun HomeTopBarSpaceFiltersSelectedPreview() = ElementPreview {
    HomeTopBar(
        selectedNavigationItem = HomeNavigationBarItem.Chats,
        currentUserAndNeighbors = persistentListOf(aMatrixUser(id = "@id:domain", displayName = USER_NAME_ALICE)),
        showAvatarIndicator = false,
        areSearchResultsDisplayed = false,
        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState()),
        onOpenSettings = {},
        onAccountSwitch = {},
        onToggleSearch = {},
        canReportBug = true,
        displayFilters = true,
        filtersState = aRoomListFiltersState(),
        spaceFiltersState = aSelectedSpaceFiltersState(),
        onMenuActionClick = {},
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@PreviewsDayNight
@Composable
internal fun HomeTopBarSpacesPreview() = ElementPreview {
    HomeTopBar(
        selectedNavigationItem = HomeNavigationBarItem.Spaces,
        currentUserAndNeighbors = persistentListOf(aMatrixUser(id = "@id:domain", displayName = USER_NAME_ALICE)),
        showAvatarIndicator = false,
        areSearchResultsDisplayed = false,
        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState()),
        onOpenSettings = {},
        onAccountSwitch = {},
        onToggleSearch = {},
        canReportBug = true,
        displayFilters = false,
        filtersState = aRoomListFiltersState(),
        spaceFiltersState = anUnselectedSpaceFiltersState(),
        onMenuActionClick = {},
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@PreviewsDayNight
@Composable
internal fun HomeTopBarWithIndicatorPreview() = ElementPreview {
    HomeTopBar(
        selectedNavigationItem = HomeNavigationBarItem.Chats,
        currentUserAndNeighbors = persistentListOf(aMatrixUser(id = "@id:domain", displayName = USER_NAME_ALICE)),
        showAvatarIndicator = true,
        areSearchResultsDisplayed = false,
        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState()),
        onOpenSettings = {},
        onAccountSwitch = {},
        onToggleSearch = {},
        canReportBug = true,
        displayFilters = true,
        filtersState = aRoomListFiltersState(),
        spaceFiltersState = anUnselectedSpaceFiltersState(),
        onMenuActionClick = {},
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@PreviewsDayNight
@Composable
internal fun HomeTopBarMultiAccountPreview() = ElementPreview {
    HomeTopBar(
        selectedNavigationItem = HomeNavigationBarItem.Chats,
        currentUserAndNeighbors = aMatrixUserList().take(3).toImmutableList(),
        showAvatarIndicator = false,
        areSearchResultsDisplayed = false,
        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState()),
        onOpenSettings = {},
        onAccountSwitch = {},
        onToggleSearch = {},
        canReportBug = true,
        displayFilters = true,
        filtersState = aRoomListFiltersState(),
        spaceFiltersState = anUnselectedSpaceFiltersState(),
        onMenuActionClick = {},
    )
}
