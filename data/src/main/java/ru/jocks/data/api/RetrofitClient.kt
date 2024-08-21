package ru.jocks.data.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.jocks.data.AddressApi
import ru.jocks.data.BusinessApi
import ru.jocks.data.FileApi
import ru.jocks.data.api.Consts.BASE_API_URL
import ru.jocks.data.api.Consts.BASE_SUGGESTIONS_API_URL
import timber.log.Timber


object RetrofitClient {
    private val businessRetrofit: Retrofit by lazy {
        val httpClient = OkHttpClient.Builder()

        val logging = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Timber.d(message)
            }
        })
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        httpClient.addInterceptor(logging)

        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'").create()

        Retrofit.Builder()
            .baseUrl(BASE_API_URL)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    private val addressRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_SUGGESTIONS_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val fileRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_API_URL)
            .build()
    }

    val fileApiService: FileApi by lazy {
        fileRetrofit.create(FileApi::class.java)
    }

    val businessApiService: BusinessApi by lazy {
        businessRetrofit.create(BusinessApi::class.java)
    }

    val suggestionsApiService: AddressApi by lazy {
        addressRetrofit.create(AddressApi::class.java)
    }

}