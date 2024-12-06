package com.example.nott_a_problem.pages.services

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class GeolocationService(private val context: Context) {

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    // Check if location permissions are granted
    private fun isPermissionGranted(): Boolean {
        return ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

    // Fetch the current location
    suspend fun getCurrentLocation(): Location? {
        return try {
            if (isPermissionGranted()) {
                fusedLocationClient.lastLocation.await()
            } else {
                // Return null or handle lack of permissions gracefully
                null
            }
        } catch (e: SecurityException) {
            // Handle cases where permissions are revoked during runtime
            e.printStackTrace()
            null
        } catch (e: Exception) {
            // Handle other exceptions (e.g., location service disabled)
            e.printStackTrace()
            null
        }
    }

    // Get continuous location updates
    fun getLocationUpdates(): Flow<Location> {
        return callbackFlow {
            if (isPermissionGranted()) {
                val locationCallback = object : com.google.android.gms.location.LocationCallback() {
                    override fun onLocationResult(locationResult: com.google.android.gms.location.LocationResult) {
                        locationResult.locations.forEach { location ->
                            trySend(location).isSuccess
                        }
                    }
                }

                try {
                    // Request location updates
                    fusedLocationClient.requestLocationUpdates(
                        com.google.android.gms.location.LocationRequest.create().apply {
                            interval = 10000  // Update interval in ms
                            fastestInterval = 5000 // Fastest interval in ms
                            priority = com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
                        },
                        locationCallback,
                        null // Default looper
                    )

                    // Stop updates when the flow is closed
                    awaitClose {
                        fusedLocationClient.removeLocationUpdates(locationCallback)
                    }
                } catch (e: SecurityException) {
                    // Handle cases where permissions are revoked during runtime
                    e.printStackTrace()
                    close(e) // Close the flow with an exception
                }
            } else {
                close() // Close the flow if permissions are not granted
            }
        }
    }
}




