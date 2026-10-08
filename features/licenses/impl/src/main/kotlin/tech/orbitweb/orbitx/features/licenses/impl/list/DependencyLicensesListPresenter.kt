/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.licenses.impl.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dev.zacsweers.metro.Inject
import tech.orbitweb.orbitx.features.licenses.impl.LicensesProvider
import tech.orbitweb.orbitx.features.licenses.impl.model.DependencyLicenseItem
import tech.orbitweb.orbitx.libraries.architecture.AsyncData
import tech.orbitweb.orbitx.libraries.architecture.Presenter
import tech.orbitweb.orbitx.libraries.core.extensions.runCatchingExceptions
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Inject
class DependencyLicensesListPresenter(
    private val licensesProvider: LicensesProvider,
) : Presenter<DependencyLicensesListState> {
    @Composable
    override fun present(): DependencyLicensesListState {
        var licenses by remember {
            mutableStateOf<AsyncData<ImmutableList<DependencyLicenseItem>>>(AsyncData.Loading())
        }
        var filteredLicenses by remember {
            mutableStateOf<AsyncData<ImmutableList<DependencyLicenseItem>>>(AsyncData.Loading())
        }
        var filter by remember { mutableStateOf("") }
        LaunchedEffect(Unit) {
            runCatchingExceptions {
                licenses = AsyncData.Success(licensesProvider.provides().toImmutableList())
            }.onFailure {
                licenses = AsyncData.Failure(it)
            }
        }
        LaunchedEffect(filter, licenses.dataOrNull()) {
            val data = licenses.dataOrNull()
            val safeFilter = filter.trim()
            if (data != null && safeFilter.isNotEmpty()) {
                filteredLicenses = AsyncData.Success(data.filter {
                    it.safeName.contains(safeFilter, ignoreCase = true) ||
                        it.groupId.contains(safeFilter, ignoreCase = true) ||
                        it.artifactId.contains(safeFilter, ignoreCase = true)
                }.toImmutableList())
            } else {
                filteredLicenses = licenses
            }
        }

        fun handleEvent(event: DependencyLicensesListEvent) {
            when (event) {
                is DependencyLicensesListEvent.SetFilter -> {
                    filter = event.filter
                }
            }
        }

        return DependencyLicensesListState(
            licenses = filteredLicenses,
            filter = filter,
            eventSink = ::handleEvent,
        )
    }
}
