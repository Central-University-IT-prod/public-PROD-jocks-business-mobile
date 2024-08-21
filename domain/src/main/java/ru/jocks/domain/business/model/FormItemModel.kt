package ru.jocks.domain.business.model

data class FormItemModel(
    val id: Int?,
    var name: String,
    val type: String = "STAR"
)