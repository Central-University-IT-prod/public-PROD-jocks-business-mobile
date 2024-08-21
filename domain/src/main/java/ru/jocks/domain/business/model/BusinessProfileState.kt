package ru.jocks.domain.business.model

interface BusinessProfileState {
    data object None :  BusinessProfileState
    data class Success(val businessProfile: BusinessInfoModel) :  BusinessProfileState
    data object Loading :  BusinessProfileState
    data class Error(val message : String) :  BusinessProfileState
}
