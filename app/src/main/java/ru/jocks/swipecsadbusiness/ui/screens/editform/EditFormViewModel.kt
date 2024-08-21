package ru.jocks.swipecsadbusiness.ui.screens.editform

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.jocks.domain.business.model.BusinessEditFormState
import ru.jocks.domain.business.model.BusinessFormState
import ru.jocks.domain.business.model.FormItemModel
import ru.jocks.domain.business.repository.BusinessRepository

class EditFormViewModel(private val businessRepository: BusinessRepository) : ViewModel() {
    var editFormState : BusinessEditFormState by mutableStateOf(BusinessEditFormState.None)
        private set

    var formRemoteState : BusinessFormState by mutableStateOf(BusinessFormState.None)
        private set

    init {
        getForm()
    }

    fun updateForm(
        fields: List<FormItemModel>
    ) = viewModelScope.launch {
        editFormState = BusinessEditFormState.Loading

        editFormState = businessRepository.updateForm(
            fields
        )
    }

    private fun getForm() = viewModelScope.launch {
        formRemoteState = BusinessFormState.Loading

        formRemoteState = businessRepository.getBusinessForm()
    }
}