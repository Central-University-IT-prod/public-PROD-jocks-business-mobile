package ru.jocks.domain.business.model

data class BusinessBaseRatingModel(
    val name: String,
    val type: String,
    val rating: Float,
    val reviews_count: Int = -1
)
