package ru.jocks.domain.business.model


sealed interface BusinessEditFormState {
    data object None :  BusinessEditFormState
    data object Success :  BusinessEditFormState
    data object Loading :  BusinessEditFormState
    data class Error(val message : String) :  BusinessEditFormState
}