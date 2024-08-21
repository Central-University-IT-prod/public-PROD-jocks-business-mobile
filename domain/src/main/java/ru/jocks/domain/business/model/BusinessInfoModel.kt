package ru.jocks.domain.business.model

data class BusinessInfoModel(
    val id: Int,
    val name: String,
    val description: String,
    val addresses: List<BusinessAddressModel>,
    val ratingAverage: Float,
    val ratingCount: Int,
    val items: List<BusinessItemModel>,
    val rating: List<BusinessBaseRatingModel> = listOf()
)
