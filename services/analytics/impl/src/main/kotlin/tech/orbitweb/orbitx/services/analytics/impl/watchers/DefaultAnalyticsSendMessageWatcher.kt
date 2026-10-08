/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.services.analytics.impl.watchers

import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.SingleIn
import tech.orbitweb.orbitx.libraries.di.RoomScope
import tech.orbitweb.orbitx.libraries.di.annotations.RoomCoroutineScope
import tech.orbitweb.orbitx.libraries.matrix.api.core.TransactionId
import tech.orbitweb.orbitx.libraries.matrix.api.room.JoinedRoom
import tech.orbitweb.orbitx.libraries.matrix.api.room.SendQueueUpdate
import tech.orbitweb.orbitx.services.analytics.api.AnalyticsService
import tech.orbitweb.orbitx.services.analytics.api.watchers.AnalyticsSendMessageWatcher
import tech.orbitweb.orbitx.services.analyticsproviders.api.AnalyticsTransaction
import tech.orbitweb.orbitx.services.analyticsproviders.api.AnalyticsTransactions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import java.util.concurrent.ConcurrentHashMap

private const val TAG = "SendMessageWatcher"

@SingleIn(RoomScope::class)
@ContributesBinding(RoomScope::class)
class DefaultAnalyticsSendMessageWatcher(
    private val room: JoinedRoom,
    private val analyticsService: AnalyticsService,
    @RoomCoroutineScope private val coroutineScope: CoroutineScope,
) : AnalyticsSendMessageWatcher {
    private val pendingEvents = ConcurrentHashMap<TransactionId, AnalyticsTransaction>()
    private var sendQueueWatchJob: Job? = null

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun start() {
        Timber.tag(TAG).d("Starting SendMessageWatcher")
        sendQueueWatchJob?.cancel()
        sendQueueWatchJob = room.subscribeToSendQueueUpdates()
            .onEach { update ->
                // We received a new local event
                when (update) {
                    is SendQueueUpdate.NewLocalEvent -> {
                        Timber.tag(TAG).d("Event with transaction id ${update.transactionId} sent")
                        watch(update.transactionId)
                    }
                    is SendQueueUpdate.SentEvent -> {
                        val pendingTransaction = pendingEvents.remove(update.transactionId)
                        if (pendingTransaction != null) {
                            Timber.tag(TAG).d("Sent event with transaction id ${update.transactionId} received in sync")
                            pendingTransaction.finish()
                        }
                    }
                    else -> Unit
                }
            }
            .launchIn(coroutineScope)
    }

    override fun stop() {
        Timber.tag(TAG).d("Stopping SendMessageWatcher")
        sendQueueWatchJob?.cancel()
        sendQueueWatchJob = null
        pendingEvents.clear()
    }

    private fun watch(transactionId: TransactionId) {
        pendingEvents[transactionId] = with(AnalyticsTransactions.sendMessage) {
            analyticsService.startTransaction(
                name = name,
                operation = operation,
                description = description,
            )
        }
    }
}
