package ru.jocks.swipecsadbusiness.ui.common

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent
import ru.jocks.data.address.responses.SuggetionReponse
import ru.jocks.domain.address.repository.AddressRepository

class ProfileFormViewModel(private val addressRepository : AddressRepository) : ViewModel() {
    val suggestionsState = mutableStateOf<List<String>>(emptyList())

    fun getSuggestions(query: String)  = viewModelScope.launch {
        suggestionsState.value = addressRepository.getSuggestion(query)
    }
}