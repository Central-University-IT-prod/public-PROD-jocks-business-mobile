package ru.jocks.swipecsadbusiness.nav

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.jocks.swipecsadbusiness.R

sealed class Destinations(val route: String, @DrawableRes val icon: Int, @StringRes val name: Int) {
    data object Analytics : Destinations("main", R.drawable.analytics, R.string.analytics_screen_name)
    data object Feedbacks : Destinations("map", R.drawable.feedbacks, R.string.feedbacks_screen_name)
    data object Settings : Destinations("profile", R.drawable.settings, R.string.settings_screen_name)
}

enum class AppScreens {
    Registration,
    Intro,
    Login,
    AddCoupon,
    EditForm
}