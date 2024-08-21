package ru.jocks.swipecsadbusiness.ui.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.jocks.domain.business.model.BusinessLoginState
import ru.jocks.domain.business.model.BusinessRegisterState
import ru.jocks.domain.business.repository.BusinessRepository
import timber.log.Timber

class LoginViewModel(private val businessRepository: BusinessRepository) : ViewModel() {
    var loginState : BusinessLoginState by mutableStateOf(BusinessLoginState.None)
        private set


    fun login(
        email: String,
        password: String
    ) = viewModelScope.launch {
        loginState = BusinessLoginState.Loading

        loginState = businessRepository.loginBusiness(
            email,
            password
        )
    }
}