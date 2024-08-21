package ru.jocks.swipecsadbusiness.ui.screens.editcoupon

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.jocks.domain.business.model.CouponUpdateState
import ru.jocks.domain.business.repository.BusinessRepository

class CouponEditViewModel(private val businessRepository: BusinessRepository) : ViewModel() {
    var couponEditState : CouponUpdateState by mutableStateOf(CouponUpdateState.None)
        private set

    fun updateCoupon(
        text: String,
        description: String
    ) = viewModelScope.launch {
        couponEditState = CouponUpdateState.Loading

        couponEditState = businessRepository.updateCoupon(
            text,
            description
        )
    }
}