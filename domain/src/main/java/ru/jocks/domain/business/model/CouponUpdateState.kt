package ru.jocks.domain.business.model

sealed interface CouponUpdateState {
    data object None :  CouponUpdateState
    data object Success :  CouponUpdateState
    data object Loading :  CouponUpdateState
    data class Error(val message : String) :  CouponUpdateState
}