package com.example.pruebas.ui.activities

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.pruebas.R
import com.example.pruebas.databinding.ActivityNotificationBinding
import com.example.pruebas.ui.utilities.BroadcasterNotifications
import java.util.Calendar

class NotificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationBinding
    val CHANNEL : String = "Hola mundo"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createNotificationChannel()


        binding.btnNotificacion.setOnClickListener{
            sendNotification()
        }


        binding.btnNotiprogramada.setOnClickListener{
            val calendar= Calendar.getInstance()
            val hora = binding.time.hour
            val minutes= binding.time.minute

            Toast.makeText(this, "La notificacion se activa a las: $hora con $minutes", Toast.LENGTH_SHORT).show()

            calendar.set(Calendar.HOUR, hora)
            calendar.set(Calendar.MINUTE, minutes)
            calendar.set(Calendar.SECOND,0)

            sendNotificationTimePicker(calendar.timeInMillis)

        }


    }

    private fun sendNotificationTimePicker(time : Long){
        Log.d("UCE", "entra al metodo")
        val myIntent= Intent(applicationContext, BroadcasterNotifications::class.java)
        val myPendingIntent= PendingIntent.getBroadcast(
            applicationContext,
            0,
            myIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager= getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,time, myPendingIntent)
        Log.d("UCE", "termina")
    }

    @SuppressLint("MissingPermission")
    fun sendNotification(){
        val largeIcon = BitmapFactory.decodeResource(resources, R.drawable.gato_perfil)

        val noti=NotificationCompat.Builder(this, CHANNEL)
        noti.setContentTitle("Dispositivos")
        noti.setContentText("David")
        noti.setSmallIcon(R.drawable.baseline_message)
        noti.setLargeIcon(largeIcon)

        noti.setPriority(NotificationCompat.PRIORITY_HIGH)
        noti.setStyle(
            NotificationCompat.BigTextStyle()
                .bigText("Mensaje de prueba para notificaciones")
        )

        with(NotificationManagerCompat.from(this)){
            notify(1,noti.build())
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Variedades"
            val descriptionText = "Notificaciones simples"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

            Log.d("UCE", "Canal de notificación creado: $CHANNEL")
        }
    }


}