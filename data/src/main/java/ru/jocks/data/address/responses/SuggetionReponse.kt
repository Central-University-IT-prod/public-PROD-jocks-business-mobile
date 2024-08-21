package ru.jocks.data.address.responses

import com.google.gson.annotations.SerializedName

data class SuggetionReponse(
    @SerializedName("suggestions")
    val suggestions: List<Suggestion>
){

    data class Suggestion(
        @SerializedName("value")
        val value: String
    )


}
