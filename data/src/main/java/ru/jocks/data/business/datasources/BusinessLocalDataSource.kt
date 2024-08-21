package ru.jocks.data.business.datasources

import android.content.Context
import android.content.SharedPreferences
import ru.jocks.domain.business.model.AuthTokenModel
import timber.log.Timber

class BusinessLocalDataSource(private val context : Context) {

    private val businessPreferences: SharedPreferences = context.getSharedPreferences("business", Context.MODE_PRIVATE)

    fun saveTokens(
        authTokenModel: AuthTokenModel
    ) {
        businessPreferences.edit().putString(Constants.ACCESS_TOKEN, authTokenModel.accessToken).apply()
        businessPreferences.edit().putString(Constants.BUSINESS_ID, authTokenModel.businessId).apply()
    }

    fun deleteTokens() {
        businessPreferences.edit().clear().apply()
    }

    fun getTokens(): AuthTokenModel? {
        val accessToken = businessPreferences.getString(Constants.ACCESS_TOKEN, null)
        val businessId = businessPreferences.getString(Constants.BUSINESS_ID, null)

        if (accessToken == null || businessId == null) {
            return null
        }
        Timber.i("get tokens $accessToken $businessId")
        return AuthTokenModel(
            accessToken = accessToken,
            businessId = businessId
        )
    }

    internal object Constants {
        const val ACCESS_TOKEN = "accessToken"
        const val BUSINESS_ID = "businessId"
    }
}