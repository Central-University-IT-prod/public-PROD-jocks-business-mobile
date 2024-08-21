package ru.jocks.swipecsadbusiness.ui.screens.analytics

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.jocks.domain.business.model.BusinessProfileState
import ru.jocks.domain.business.repository.BusinessRepository
import timber.log.Timber

class AnalyticViewModel(private val businessRepository: BusinessRepository) : ViewModel() {
    var analyticState: BusinessProfileState by mutableStateOf(BusinessProfileState.None)
        private set

    init {
        getBusinessAnalytic()
    }

    private fun getBusinessAnalytic() = viewModelScope.launch {
        analyticState = BusinessProfileState.Loading

        analyticState = businessRepository.getBusinessInfo()
        Timber.tag("Analytic").i(analyticState.toString())
    }
}