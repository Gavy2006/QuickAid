package com.example.quickaid.repositry

import android.util.Log
import com.example.quickaid.models.Ambulance
import com.example.quickaid.models.UserRole
import com.example.quickaid.supabase.SupabaseClientProvider
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.postgrest.postgrest

class AuthRepository {

    private val client = SupabaseClientProvider.client

    suspend fun signUp(
        email: String,
        password: String,
        driverName: String,
        phone: String,
        ambulanceNumber: String,
        vehicleType: String
    ) {
        val result = client.auth.signUpWith(Email) {
            this.email = email
            this.password = password
        }

        Log.d("QuickAid", "Signup Result = $result")

        val session = client.auth.currentSessionOrNull()
        Log.d("QuickAid", "Session = $session")

        val user = client.auth.currentUserOrNull()
        Log.d("QuickAid", "Current User = $user")

        if (user == null) {
            throw Exception("Current User is NULL")
        }

        try {
            client.postgrest["user_roles"].insert(
                UserRole(
                    auth_id = user.id,
                    role = "driver"
                )
            )
            Log.d("QuickAid", "user_roles inserted")
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception("user_roles: ${e.message}")
        }

        try {
            client.postgrest["ambulances"].insert(
                Ambulance(
                    auth_id = user.id,
                    driver_name = driverName,
                    driver_phone = phone,
                    driver_license = "",
                    vehicle_reg = ambulanceNumber,
                    vehicle_type = vehicleType,
                    equipment = "",
                    zone = "",
                    profile_photo = "",
                    is_active = false
                )
            )
            Log.d("QuickAid", "ambulances inserted")
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception("ambulances: ${e.message}")
        }
    }


    suspend fun login(
        email: String,
        password: String
    ) {

        client.auth.signInWith(Email) {
            this.email = email
            this.password = password
        }

        Log.d("QuickAid", "Login Successful")

        val user = client.auth.currentUserOrNull()

        Log.d("QuickAid", "Logged In User = $user")

        if (user == null) {
            throw Exception("Login failed")
        }

    }

    fun isLoggedIn(): Boolean {
        return client.auth.currentSessionOrNull() != null
    }

    suspend fun getProfile(): Ambulance {

        val user = client.auth.currentUserOrNull()
            ?: throw Exception("User not logged in")

        return client.postgrest["ambulances"]
            .select {
                filter {
                    eq("auth_id", user.id)
                }
            }
            .decodeSingle<Ambulance>()

    }

    suspend fun updateProfile(
        driverName: String,
        phone: String,
        driverLicense: String,
        vehicleReg: String,
        vehicleType: String,
        equipment: String,
        zone: String,
        isActive: Boolean
    ) {

        Log.d("QuickAid", "==============================")
        Log.d("QuickAid", "updateProfile() called")

        val user = client.auth.currentUserOrNull()
            ?: throw Exception("User not logged in")

        Log.d("QuickAid", "User ID = ${user.id}")

        Log.d("QuickAid", "Updating values:")
        Log.d("QuickAid", "driverName = $driverName")
        Log.d("QuickAid", "phone = $phone")
        Log.d("QuickAid", "driverLicense = $driverLicense")
        Log.d("QuickAid", "vehicleReg = $vehicleReg")
        Log.d("QuickAid", "vehicleType = $vehicleType")
        Log.d("QuickAid", "equipment = $equipment")
        Log.d("QuickAid", "zone = $zone")
        Log.d("QuickAid", "isActive = $isActive")

        try {

            client.postgrest["ambulances"]
                .update({
                    set("driver_name", driverName)
                    set("driver_phone", phone)
                    set("driver_license", driverLicense)
                    set("vehicle_reg", vehicleReg)
                    set("vehicle_type", vehicleType)
                    set("equipment", equipment)
                    set("zone", zone)
                    set("is_active", isActive)
                }) {
                    filter {
                        eq("auth_id", user.id)
                    }
                }

            Log.d("QuickAid", "✅ Profile updated successfully")

        } catch (e: Exception) {

            Log.e("QuickAid", "❌ Update failed", e)
            throw e
        }
    }




    suspend fun updateDriverLocation(
        lat: Double,
        lng: Double
    ) {

        val user = client.auth.currentUserOrNull()
            ?: throw Exception("User not logged in")

        client.postgrest["ambulances"]
            .update({

                set(
                    "base_location",
                    "POINT($lng $lat)"
                )

            }) {
                filter {
                    eq("auth_id", user.id)
                }
            }

        Log.d(
            "QuickAid",
            "Location Updated -> $lat , $lng"
        )
    }
}