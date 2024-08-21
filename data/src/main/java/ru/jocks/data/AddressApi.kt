package ru.jocks.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Streaming
import ru.jocks.data.address.responses.SuggetionReponse

interface AddressApi {
    @GET("address/")
    @Streaming
    suspend fun getAddress(
        @Query("addresslim") limit: Int,
        @Query("output") output: String,
        @Query("query") query: String
    ) : Response<SuggetionReponse>
}