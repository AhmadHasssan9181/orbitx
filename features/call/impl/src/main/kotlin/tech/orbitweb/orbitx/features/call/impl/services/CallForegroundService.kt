/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.call.impl.services

import android.Manifest
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.PendingIntentCompat
import androidx.core.app.ServiceCompat
import androidx.core.content.ContextCompat
import tech.orbitweb.orbitx.features.call.impl.R
import tech.orbitweb.orbitx.features.call.impl.ui.ElementCallActivity
import tech.orbitweb.orbitx.libraries.core.extensions.runCatchingExceptions
import tech.orbitweb.orbitx.libraries.designsystem.utils.CommonDrawables
import tech.orbitweb.orbitx.libraries.push.api.notifications.ForegroundServiceType
import tech.orbitweb.orbitx.libraries.push.api.notifications.NotificationIdProvider
import timber.log.Timber

/**
 * A foreground service that shows a notification for an ongoing call while the UI is in background.
 */
class CallForegroundService : Service() {
    companion object {
        fun start(context: Context) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(context, CallForegroundService::class.java)
                ContextCompat.startForegroundService(context, intent)
            } else {
                Timber.w("Microphone permission is not granted, cannot start the call foreground service")
            }
        }

        fun stop(context: Context) {
            val intent = Intent(context, CallForegroundService::class.java)
            context.stopService(intent)
        }
    }

    private lateinit var notificationManagerCompat: NotificationManagerCompat

    override fun onCreate() {
        super.onCreate()

        notificationManagerCompat = NotificationManagerCompat.from(this)

        val foregroundServiceChannel = NotificationChannelCompat.Builder(
            "call_foreground_service_channel",
            NotificationManagerCompat.IMPORTANCE_DEFAULT,
        ).setName(
            getString(R.string.call_foreground_service_channel_title_android).ifEmpty { "Ongoing call" }
        ).build()
        notificationManagerCompat.createNotificationChannel(foregroundServiceChannel)

        val callActivityIntent = Intent(this, ElementCallActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        val pendingIntent = PendingIntentCompat.getActivity(this, 0, callActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT, false)

        val hangUpIntent = Intent(this, ElementCallActivity::class.java).apply {
            action = ElementCallActivity.ACTION_HANGUP
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        val hangUpPendingIntent = PendingIntentCompat.getActivity(this, 101, hangUpIntent, PendingIntent.FLAG_UPDATE_CURRENT, false)

        val caller = androidx.core.app.Person.Builder()
            .setName("Orbit Call")
            .setImportant(true)
            .build()

        val notification = NotificationCompat.Builder(this, foregroundServiceChannel.id)
            .setSmallIcon(CommonDrawables.ic_notification)
            .setContentTitle(getString(R.string.call_foreground_service_title_android))
            .setContentText(getString(R.string.call_foreground_service_message_android))
            .setContentIntent(pendingIntent)
            .setColor(0xFF8B5CF6.toInt())
            .setColorized(true)
            .setOngoing(true)
            .setUsesChronometer(true)
            .setShowWhen(true)
            .setWhen(System.currentTimeMillis())
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_CALL)
            .apply {
                if (hangUpPendingIntent != null) {
                    setStyle(
                        NotificationCompat.CallStyle.forOngoingCall(caller, hangUpPendingIntent)
                    )
                    addAction(
                        0,
                        "Hang Up",
                        hangUpPendingIntent
                    )
                }
            }
            .build()
        val notificationId = NotificationIdProvider.getForegroundServiceNotificationId(ForegroundServiceType.ONGOING_CALL)
        val serviceType = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            ServiceInfo.FOREGROUND_SERVICE_TYPE_MICROPHONE
        } else {
            0
        }
        runCatchingExceptions {
            ServiceCompat.startForeground(this, notificationId, notification, serviceType)
        }.onFailure {
            Timber.e(it, "Failed to start ongoing call foreground service")
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        stopForeground(STOP_FOREGROUND_REMOVE)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
