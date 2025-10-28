package com.printology.app.services

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.printology.app.services.NotificationService

class FCMService : FirebaseMessagingService() {

    private val TAG = "FCMService"

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "From: ${remoteMessage.from}")

        // Handle notification payload
        remoteMessage.notification?.let { notification ->
            val title = notification.title ?: "Printology"
            val body = notification.body ?: "Ada notifikasi baru"

            // Show notification using NotificationService
            val notificationService = NotificationService(this)
            notificationService.showNotification(title, body)
        }

        // Handle data payload (for custom notifications)
        remoteMessage.data.let { data ->
            if (data.isNotEmpty()) {
                Log.d(TAG, "Message data payload: $data")

                when (data["type"]) {
                    "order" -> {
                        val orderId = data["orderId"] ?: ""
                        val status = data["status"] ?: ""
                        val notificationService = NotificationService(this)
                        notificationService.showOrderNotification(orderId, status)
                    }
                    "promo" -> {
                        val title = data["title"] ?: "Promo Spesial"
                        val message = data["message"] ?: "Cek promo terbaru kami!"
                        val notificationService = NotificationService(this)
                        notificationService.showPromoNotification(title, message)
                    }
                    "service" -> {
                        val service = data["service"] ?: "Layanan"
                        val message = data["message"] ?: "Update layanan"
                        val notificationService = NotificationService(this)
                        notificationService.showServiceNotification(service, message)
                    }
                }
            }
        }
    }

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")
        // Send token to server if needed
        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String) {
        // Implement sending token to your server
        Log.d(TAG, "Sending token to server: $token")
    }
}
