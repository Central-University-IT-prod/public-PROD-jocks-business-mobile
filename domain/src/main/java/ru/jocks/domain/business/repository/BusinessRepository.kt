package ru.jocks.domain.business.repository

import ru.jocks.domain.business.model.BusinessEditFormState
import ru.jocks.domain.business.model.BusinessFormState
import ru.jocks.domain.business.model.BusinessLoginState
import ru.jocks.domain.business.model.BusinessProfileState
import ru.jocks.domain.business.model.BusinessRegisterState
import ru.jocks.domain.business.model.BusinessReviewModel
import ru.jocks.domain.business.model.CouponUpdateState
import ru.jocks.domain.business.model.FormItemModel
import java.io.InputStream

interface BusinessRepository {
    suspend fun registerBusiness(
        name: String,
        description: String,
        addresses: List<String>,
        userContactEmail: String,
        email: String,
        password: String,
    ) : BusinessRegisterState

    suspend fun loginBusiness(
        email: String,
        password: String
    ) : BusinessLoginState

    suspend fun updateForm (
        names: List<FormItemModel>
    ) : BusinessEditFormState

    suspend fun updateCoupon (
        name: String,
        description: String,
    ) : CouponUpdateState

    suspend fun getReviews(
        offset: Int,
        limit: Int
    ) : List<BusinessReviewModel>

    suspend fun getBusinessForm() : BusinessFormState

    suspend fun getBusinessInfo() : BusinessProfileState

    fun getSavedBusinesses() : BusinessLoginState?

    suspend fun logoutBusiness()

    suspend fun getBusinessesStatistics()

    suspend fun getFeedbacks()

    suspend fun exportData() : InputStream
}
