package ru.jocks.domain.business.model

data class AuthTokenModel (
    val accessToken: String,
    val businessId: String
)