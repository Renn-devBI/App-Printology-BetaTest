package com.printology.app.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.printology.app.MainActivity
import com.printology.app.R

class NotificationService(private val context: Context) {

    companion object {
        const val CHANNEL_ID = "printology_notifications"
        const val CHANNEL_NAME = "Printology Notifications"
        const val CHANNEL_DESCRIPTION = "Notifikasi dari Printology"
    }

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
                description = CHANNEL_DESCRIPTION
            }

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showNotification(title: String, message: String, notificationId: Int = 1) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ai_printology)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            notify(notificationId, builder.build())
        }
    }

    fun showOrderNotification(orderId: String, status: String) {
        val title = "Update Pesanan #$orderId"
        val message = "Status pesanan Anda: $status"
        showNotification(title, message, orderId.hashCode())
    }

    fun showPromoNotification(title: String, message: String) {
        showNotification(title, message, title.hashCode())
    }

    fun showServiceNotification(service: String, message: String) {
        val title = "Layanan $service"
        showNotification(title, message, service.hashCode())
    }
}
