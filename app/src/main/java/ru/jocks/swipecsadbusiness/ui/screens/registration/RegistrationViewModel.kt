package ru.jocks.swipecsadbusiness.ui.screens.registration

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.jocks.domain.business.model.BusinessRegisterState
import ru.jocks.domain.business.repository.BusinessRepository
import timber.log.Timber

class RegistrationViewModel(private val businessRepository: BusinessRepository) : ViewModel() {
    var registerState : BusinessRegisterState by mutableStateOf(BusinessRegisterState.None)
        private set


    fun register(
        name: String,
        description: String,
        email: String,
        userContactEmail: String,
        addresses: List<String>,
        password: String
    ) = viewModelScope.launch {
        registerState = BusinessRegisterState.Loading

        registerState = businessRepository.registerBusiness(
            name,
            description,
            addresses,
            userContactEmail,
            email,
            password
        )
        Timber.i("registerState $registerState")

    }
}