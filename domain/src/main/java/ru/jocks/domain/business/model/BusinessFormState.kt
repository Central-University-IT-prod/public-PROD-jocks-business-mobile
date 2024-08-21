package ru.jocks.domain.business.model

interface BusinessFormState {
    data object None :  BusinessFormState
    data class Success(val fields: List<String>) :  BusinessFormState
    data object Loading :  BusinessFormState
    data class Error(val message : String) :  BusinessFormState
}