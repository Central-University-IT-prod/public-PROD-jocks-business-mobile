package ru.jocks.domain.business.model

data class BusinessItemModel (
    val id: String,
    val name: String,
    val rating: Float,
    val ratingCount: Int
)