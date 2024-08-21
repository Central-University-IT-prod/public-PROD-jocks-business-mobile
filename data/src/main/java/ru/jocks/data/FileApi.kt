package ru.jocks.data

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Streaming
import ru.jocks.data.business.responses.AuthResponse
import ru.jocks.domain.business.model.BusinessCreateModel
import ru.jocks.domain.business.model.BusinessInfoModel
import ru.jocks.domain.business.model.BusinessLoginModel
import ru.jocks.domain.business.model.BusinessReviewModel
import ru.jocks.domain.business.model.CouponModel
import ru.jocks.domain.business.model.FormItemModel

interface FileApi {

    @GET("/api/producer/export_data/{id}")
    @Streaming
    fun exportData(@Path("id") business: Int): Call<ResponseBody>
}