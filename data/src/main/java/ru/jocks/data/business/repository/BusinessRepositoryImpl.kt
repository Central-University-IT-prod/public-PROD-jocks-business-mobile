package ru.jocks.data.business.repository

import org.json.JSONException
import org.json.JSONObject
import org.koin.java.KoinJavaComponent
import ru.jocks.data.api.RetrofitClient
import ru.jocks.data.business.datasources.BusinessLocalDataSource
import ru.jocks.domain.business.model.AuthTokenModel
import ru.jocks.domain.business.model.BusinessCreateModel
import ru.jocks.domain.business.model.BusinessEditFormState
import ru.jocks.domain.business.model.BusinessFormState
import ru.jocks.domain.business.model.BusinessInfoModel
import ru.jocks.domain.business.model.BusinessItemModel
import ru.jocks.domain.business.model.BusinessLoginModel
import ru.jocks.domain.business.model.BusinessLoginState
import ru.jocks.domain.business.model.BusinessProfileState
import ru.jocks.domain.business.model.BusinessRegisterState
import ru.jocks.domain.business.model.BusinessReviewModel
import ru.jocks.domain.business.model.CouponModel
import ru.jocks.domain.business.model.CouponUpdateState
import ru.jocks.domain.business.model.FormItemModel
import ru.jocks.domain.business.repository.BusinessRepository
import timber.log.Timber
import java.io.InputStream

class BusinessRepositoryImpl : BusinessRepository {
    private val apiClient = RetrofitClient.businessApiService
    private val fileClient = RetrofitClient.fileApiService
    private val businessLocalDataSource by KoinJavaComponent.inject<BusinessLocalDataSource>(
        BusinessLocalDataSource::class.java
    )

    override suspend fun registerBusiness(
        name: String,
        description: String,
        addresses: List<String>,
        userContactEmail: String,
        email: String,
        password: String
    ): BusinessRegisterState {
        try {
            val response = apiClient.registerUser(
                BusinessCreateModel(
                    name = name,
                    description = description,
                    addresses = addresses,
                    userContactEmail = userContactEmail,
                    email = email,
                    password = password
                )
            )
            Timber.i("response registerUser $response")
            return if (response.isSuccessful) {
                val business = loginBusiness(email, password)
                Timber.i("loginBusiness business $business")
                if (business is BusinessLoginState.Success) {
                    businessLocalDataSource.saveTokens(
                        AuthTokenModel(
                            accessToken = business.token,
                            businessId = business.businessId
                        )
                    )
                    Timber.i("check after save ${businessLocalDataSource.getTokens()}")
                    BusinessRegisterState.Success
                } else {
                    BusinessRegisterState.Error("Login failed")
                }
            } else {
                val encodedJson = response.errorBody()?.string()
                try {
                    var error = ""
                    if (encodedJson != null) {
                        error = JSONObject(encodedJson).get("reason") as String
                    }
                    return BusinessRegisterState.Error(error)
                } catch (e: JSONException) {
                    return BusinessRegisterState.Error(
                        message = "Unknown exception"
                    )
                }
            }
        } catch (e: Exception) {
            return BusinessRegisterState.Error(
                message = "Unknown exception"
            )
        }
    }

    override suspend fun loginBusiness(email: String, password: String): BusinessLoginState {
        try {
            val response = apiClient.loginUser(
                BusinessLoginModel(
                    email,
                    password
                )
            )

            Timber.i("response loginUser $response")


            return if (response.isSuccessful) {
                val body = response.body() ?: throw Exception("Body is null")
                Timber.i("loginBusiness business $body")
                businessLocalDataSource.saveTokens(
                    AuthTokenModel(
                        accessToken = body.accessToken,
                        businessId = body.businessId
                    )
                )
                Timber.i("check after save ${businessLocalDataSource.getTokens()}")
                BusinessLoginState.Success(body.accessToken, body.businessId)
            } else {
                val encodedJson = response.errorBody()?.string()
                try {
                    var error = ""
                    if (encodedJson != null) {
                        error = JSONObject(encodedJson).get("reason") as String
                    }
                    return BusinessLoginState.Error(error)
                } catch (e: JSONException) {
                    Timber.e(e)
                    return BusinessLoginState.Error(
                        message = "Unknown exception"
                    )
                }
            }
        } catch (e: Exception) {
            Timber.e(e)
            return BusinessLoginState.Error(
                message = "Unknown exception"
            )
        }
    }

    override suspend fun updateForm(names: List<FormItemModel>): BusinessEditFormState {
        try {
            val response = apiClient.updateForm(
                fields = names,
                business = businessLocalDataSource.getTokens()?.businessId ?: ""
            )

            if (!response.isSuccessful) {
                val encodedJson = response.errorBody()?.string()
                try {
                    var error = ""
                    if (encodedJson != null) {
                        error = JSONObject(encodedJson).get("reason") as String
                    }
                    Timber.e(error)
                } catch (e: JSONException) {
                    Timber.e(e)
                    return BusinessEditFormState.Error(
                        message = "Unknown exception"
                    )
                }
            }

            return BusinessEditFormState.Success
        } catch (e: Exception) {
            Timber.e(e)
            return BusinessEditFormState.Error(
                message = "Unknown exception"
            )
        }
    }

    override suspend fun updateCoupon(name: String, description: String): CouponUpdateState {
        try {
            val response = apiClient.updateCoupon(
                coupon = CouponModel(
                    name,
                    description
                ),
                business = businessLocalDataSource.getTokens()?.businessId ?: ""
            )

            if (!response.isSuccessful) {
                val encodedJson = response.errorBody()?.string()
                try {
                    var error = ""
                    if (encodedJson != null) {
                        error = JSONObject(encodedJson).get("reason") as String
                    }
                    Timber.e(error)
                } catch (e: JSONException) {
                    Timber.e(e)
                    return CouponUpdateState.Error(
                        message = "Unknown exception"
                    )
                }
            }

            return CouponUpdateState.Success
        } catch (e: Exception) {
            Timber.e(e)
            return CouponUpdateState.Error(
                message = "Unknown exception"
            )
        }
    }

    override suspend fun getReviews(offset: Int, limit: Int): List<BusinessReviewModel> {
        try {
            val business = businessLocalDataSource.getTokens()
            Timber.i("get business $business")
            if (business == null) return listOf()

            val response = apiClient.getReviews(business.businessId)
            Timber.i(response.toString())
            return if (response.isSuccessful) {
                response.body() ?: throw Exception("Body is null")
            } else {
                val encodedJson = response.errorBody()?.string()
                try {
                    var error = ""
                    if (encodedJson != null) {
                        error = JSONObject(encodedJson).get("reason") as String
                    }
                    Timber.e(error)
                    listOf()
                } catch (e: JSONException) {
                    listOf()
                }
            }
        } catch (e: Exception) {
            Timber.e(e)
            return listOf()
        }
    }

    override suspend fun getBusinessForm(): BusinessFormState {
        try {
            val business = businessLocalDataSource.getTokens()
            Timber.i("get business $business")
            if (business == null) return BusinessFormState.Error("Not auth")

            val response = apiClient.getBusinessForm(business.businessId)
            if (response.isSuccessful) {
                val body = response.body() ?: throw Exception("Body is null")
                return BusinessFormState.Success(
                    body.map {
                        it.name
                    }
                )
            } else {
                val encodedJson = response.errorBody()?.string()
                return try {
                    var error = ""
                    if (encodedJson != null) {
                        error = JSONObject(encodedJson).get("reason") as String
                    }
                    BusinessFormState.Error(error)
                } catch (e: JSONException) {
                    BusinessFormState.Error(
                        message = "Unknown exception"
                    )
                }
            }
        } catch (e: Exception) {
            Timber.e(e)
            return BusinessFormState.Error(
                message = "Unknown exception"
            )
        }
    }

    override suspend fun getBusinessInfo(): BusinessProfileState {
        try {
            val business = businessLocalDataSource.getTokens()
            Timber.i("get business $business")
            if (business == null) return BusinessProfileState.Error("Not auth")

            val response = apiClient.getBusinessesProfile(business.businessId)
            Timber.i(response.toString())
            if (response.isSuccessful) {
                val body = response.body() ?: throw Exception("Body is null")
                return BusinessProfileState.Success(
                    BusinessInfoModel(
                        id = body.id,
                        name = body.name,
                        description = body.description,
                        addresses = listOf(),
                        ratingAverage = body.ratingAverage,
                        ratingCount = body.ratingCount,
                        items = body.items.map {
                            BusinessItemModel(it.id, it.name, it.rating, it.ratingCount)
                        },
                        rating = body.rating
                    )
                )
            } else {
                val encodedJson = response.errorBody()?.string()
                return try {
                    var error = ""
                    if (encodedJson != null) {
                        error = JSONObject(encodedJson).get("reason") as String
                    }
                    BusinessProfileState.Error(error)
                } catch (e: JSONException) {
                    BusinessProfileState.Error(
                        message = "Unknown exception"
                    )
                }
            }
        } catch (e: Exception) {
            Timber.e(e)
            return BusinessProfileState.Error(
                message = "Unknown exception"
            )
        }
    }

    override fun getSavedBusinesses(): BusinessLoginState? {
        val business = businessLocalDataSource.getTokens()
        Timber.i("get saved business $business")
        if (business != null) return BusinessLoginState.Success(
            business.accessToken,
            business.businessId
        )
        return null
    }

    override suspend fun logoutBusiness() {
        businessLocalDataSource.deleteTokens()
    }

    override suspend fun getBusinessesStatistics() {
        TODO("Not yet implemented")
    }

    override suspend fun getFeedbacks() {
        TODO("Not yet implemented")
    }

    override suspend fun exportData(): InputStream {
        val business = businessLocalDataSource.getTokens()
        val response = fileClient.exportData(business?.businessId?.toInt()!!).execute()
        return response.body()?.byteStream()!!
    }
}