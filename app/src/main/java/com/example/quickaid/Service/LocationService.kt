package com.example.quickaid.service

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.quickaid.utils.LocationHelper
import com.example.quickaid.repositry.AuthRepository
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.app.Notification
import androidx.core.app.NotificationCompat
import com.example.quickaid.R
import com.example.quickaid.utils.NotificationHelper


class LocationService : Service() {

    private lateinit var locationHelper: LocationHelper
    private val repository = AuthRepository()
    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()

        NotificationHelper.createChannel(this)

        locationHelper = LocationHelper(this)

        Log.d("LocationService", "Started")
    }

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int
    ): Int {

        Log.d("QuickAid", "Calling startForeground()")

        startForeground(
            1,
            createNotification()
        )
        Log.d("LocationService", "Service Running")

        locationHelper.startLocationUpdates { location ->

            Log.d(
                "DriverLocation",
                "Lat=${location.latitude}, Lng=${location.longitude}"
            )

            CoroutineScope(Dispatchers.IO).launch {

                try {

                    repository.updateDriverLocation(
                        location.latitude,
                        location.longitude
                    )

                } catch (e: Exception) {

                    Log.e(
                        "QuickAid",
                        "Location update failed",
                        e
                    )
                }
            }

        }

        return START_STICKY
    }

    private fun createNotification(): Notification {

        return NotificationCompat.Builder(
            this,
            NotificationHelper.CHANNEL_ID
        )
            .setContentTitle("QuickAid Driver")
            .setContentText("Sharing Live Location")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setOngoing(true)
            .build()
    }
}