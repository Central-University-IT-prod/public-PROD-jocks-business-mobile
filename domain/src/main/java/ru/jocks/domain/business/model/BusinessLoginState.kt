package ru.jocks.domain.business.model


sealed interface BusinessLoginState {
    data object None :  BusinessLoginState
    data class Success(val token: String, val businessId: String) :  BusinessLoginState
    data object Loading :  BusinessLoginState
    data class Error(val message : String) :  BusinessLoginState
}