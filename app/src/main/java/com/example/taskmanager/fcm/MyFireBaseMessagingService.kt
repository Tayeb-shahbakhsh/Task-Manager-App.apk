package com.example.taskmanager.fcm

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFireBaseMessagingService: FirebaseMessagingService(){

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        Log.d("messageFromFirebase", "onMessageReceived: ${message.notification?.title} + ${message.notification?.body}")
    }
}