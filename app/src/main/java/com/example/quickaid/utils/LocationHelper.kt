package com.example.quickaid.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.*

class LocationHelper(
    context: Context
) {

    private val client =
        LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    fun startLocationUpdates(
        onLocation: (Location) -> Unit
    ) {

        val request = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            10000L
        )
            .setMinUpdateIntervalMillis(5000L)
            .setWaitForAccurateLocation(true)
            .build()

        client.requestLocationUpdates(
            request,
            object : LocationCallback() {

                override fun onLocationResult(result: LocationResult) {

                    result.lastLocation?.let {
                        onLocation(it)
                    }

                }
            },
            Looper.getMainLooper()
        )
    }
}