package ru.jocks.domain.business.model

sealed interface BusinessRegisterState {
    data object None : BusinessRegisterState
    data object Success : BusinessRegisterState
    data object Loading : BusinessRegisterState
    data class Error(val message : String) : BusinessRegisterState
}