package ru.jocks.swipecsadbusiness.ui.screens.feebacks

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import kotlinx.coroutines.launch
import ru.jocks.domain.business.model.BusinessInfoModel
import ru.jocks.domain.business.model.BusinessProfileState
import ru.jocks.domain.business.model.BusinessReviewModel
import ru.jocks.domain.business.repository.BusinessRepository
import timber.log.Timber

sealed interface FeedbackUiState {
    data class Success(val feedbacks: List<BusinessReviewModel>) :  FeedbackUiState
    data object Loading :  FeedbackUiState
    data class Error(val message : String) :  FeedbackUiState
}


class FeedbackViewModel(private val businessRepository: BusinessRepository) : ViewModel() {
    var feedbackState: FeedbackUiState by mutableStateOf(FeedbackUiState.Loading)
        private set


    init {
        getFeedback()
    }


     fun getFeedback() {
        try {
            viewModelScope.launch {
                feedbackState = FeedbackUiState.Loading
                feedbackState = FeedbackUiState.Success(businessRepository.getReviews(0, 100))
            }
        } catch (e : Exception) {
            feedbackState = FeedbackUiState.Error(e.message ?: "Неизвестная ошибка!")
        }
    }
}
