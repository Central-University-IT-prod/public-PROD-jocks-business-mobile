package ru.jocks.domain.business.model

data class CouponModel(
    val name: String,
    val description: String,
    val token: String = "xxxxxx"
)
