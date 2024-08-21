package ru.jocks.data.business.responses

import com.google.gson.annotations.SerializedName

data class AuthResponse (
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("business_id")
    val businessId: String
)