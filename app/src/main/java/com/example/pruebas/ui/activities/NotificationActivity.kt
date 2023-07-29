package com.example.pruebas.ui.activities

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.pruebas.R
import com.example.pruebas.databinding.ActivityNotificationBinding

class NotificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationBinding
    val CHANNEL : String = "Hola mundo"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNotificacion.setOnClickListener{
            Log.d("UCE", "Entrando")

            //La comento una vez que ya esta creado el canal
//            createNotificationChannel()
            sendNotification()
        }
    }


    @SuppressLint("MissingPermission")
    fun sendNotification() {
//        val fotoPerfil= R.drawable.gato as Bitmap
        val noti=NotificationCompat.Builder(this, CHANNEL)
        noti.setContentTitle("Dispositivos")
        noti.setContentText("Nueva Notificacion")
        noti.setSmallIcon(R.drawable.baseline_message)
//        noti.setLargeIcon(fotoPerfil)

        noti.setPriority(NotificationCompat.PRIORITY_HIGH)
        noti.setStyle(
            NotificationCompat.BigTextStyle()
                .bigText("Esta es una notificacion para recordar que estamos trabajando en android")
        )

        with(NotificationManagerCompat.from(this)){
            notify(1,noti.build())
        }
    }


    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Variedades"
            val descriptionText = "Notificaciones simples"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }




}