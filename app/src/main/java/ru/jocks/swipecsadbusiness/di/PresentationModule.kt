package ru.jocks.swipecsadbusiness.di

import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.jocks.swipecsadbusiness.ui.common.ProfileFormViewModel
import ru.jocks.swipecsadbusiness.ui.screens.analytics.AnalyticViewModel
import ru.jocks.swipecsadbusiness.ui.screens.editcoupon.CouponEditViewModel
import ru.jocks.swipecsadbusiness.ui.screens.editform.EditFormViewModel
import ru.jocks.swipecsadbusiness.ui.screens.feebacks.FeedbackViewModel
import ru.jocks.swipecsadbusiness.ui.screens.login.LoginViewModel
import ru.jocks.swipecsadbusiness.ui.screens.profile.ProfileViewModel
import ru.jocks.swipecsadbusiness.ui.screens.registration.RegistrationViewModel

val presentationModule = module {
    viewModel { ProfileFormViewModel(get()) }
    viewModel { RegistrationViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { ProfileViewModel(get(), androidApplication()) }
    viewModel { EditFormViewModel(get()) }
    viewModel { CouponEditViewModel(get()) }
    viewModel { AnalyticViewModel(get()) }
    viewModel { FeedbackViewModel(get()) }
}