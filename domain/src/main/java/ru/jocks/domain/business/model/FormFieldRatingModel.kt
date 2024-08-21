package ru.jocks.domain.business.model

data class FormFieldRatingModel (
    val name: String,
    val rating: Float,
    val reviewsCount: Int
)