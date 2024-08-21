package ru.jocks.data

import com.google.gson.annotations.SerializedName
import ru.jocks.domain.business.model.BusinessBaseRatingModel


data class BusinessItemInfoResponse (
    val id: String,
    val name: String,
    val rating: Float,
    @SerializedName("rating_count")
    val ratingCount: Int
)

data class BusinessInfoResponse (
    val id: Int,
    val name: String,
    @SerializedName("description_short")
    val description: String,
    val rating: List<BusinessBaseRatingModel>,
    @SerializedName("rating_count")
    val ratingCount: Int,
    @SerializedName("rating_average")
    val ratingAverage: Float,
    val items: List<BusinessItemInfoResponse>
)