package com.example.quickaid.models


import kotlinx.serialization.Serializable

@Serializable
data class UserRole(
    val auth_id: String,
    val role: String
)
