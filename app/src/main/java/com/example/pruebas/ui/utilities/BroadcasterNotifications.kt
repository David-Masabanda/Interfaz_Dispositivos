package com.example.pruebas.ui.utilities

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.pruebas.R
import com.example.pruebas.ui.activities.CameraActivity
import com.example.pruebas.ui.activities.NotificationActivity

class BroadcasterNotifications: BroadcastReceiver() {



    val CHANNEL : String = "Hola mundo"

    override fun onReceive(context: Context, intent: Intent) {

        val myIntent = Intent(context, CameraActivity::class.java)
        val myPendingIntent = PendingIntent.getActivity(
            context, 0,
            myIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

        val noti= NotificationCompat.Builder(context, CHANNEL)
        noti.setContentTitle("Dispositivos")
        noti.setContentText("Nueva Notificacion")
        noti.setSmallIcon(R.drawable.baseline_message)
        noti.setPriority(NotificationCompat.PRIORITY_HIGH)
        noti.setStyle(
            NotificationCompat.BigTextStyle()
                .bigText("Esta es una notificacion para recordar que estamos trabajando en android")
        )
        noti.setContentIntent(myPendingIntent)
        val notificationManager=context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, noti.build())
    }
}