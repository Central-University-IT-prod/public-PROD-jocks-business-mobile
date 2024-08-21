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

interface BusinessApi {
    @POST("/api/producer/auth/register")
    suspend fun registerUser(@Body request: BusinessCreateModel): Response<BusinessInfoModel>

    @POST("/api/producer/auth/signin")
    suspend fun loginUser(@Body request: BusinessLoginModel): Response<AuthResponse>

    @PUT("/api/producer/business/{id}/form")
    suspend fun updateForm(
        @Body fields: List<FormItemModel>,
        @Path("id") business: String
    ): Response<Boolean>

    @PUT("/api/producer/promocode/{id}/")
    suspend fun updateCoupon(
        @Path("id") business: String,
        @Body coupon: CouponModel
    ) : Response<CouponModel>

    @GET("/api/business/{id}")
    suspend fun getBusinessesProfile(@Path("id") business: String): Response<BusinessInfoResponse>

    @GET("/api/producer/business/{id}/form")
    suspend fun getBusinessForm(@Path("id") business: String): Response<List<FormItemModel>>

    @GET("/api/producer/business/reviews/{business_id}")
    suspend fun getReviews(@Path("business_id") business: String): Response<List<BusinessReviewModel>>

}