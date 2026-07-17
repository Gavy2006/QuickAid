package com.example.quickaid.models


import kotlinx.serialization.Serializable

@Serializable
data class Profile(
    val auth_id: String,
    val driver_name: String,
    val driver_phone: String,
    val driver_license: String? = null,
    val vehicle_reg: String,
    val vehicle_type: String,
    val equipment: String? = null,
    val zone: String? = null,
    val profile_photo: String? = null,
    val is_active: Boolean = true
)