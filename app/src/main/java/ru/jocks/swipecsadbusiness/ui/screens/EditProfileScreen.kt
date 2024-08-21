package ru.jocks.swipecsadbusiness.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.jocks.swipecsadbusiness.R
import ru.jocks.swipecsadbusiness.ui.common.ProfileForm

@Composable
fun EditProfileScreen() {
    ProfileForm(
        actionName = stringResource(id = R.string.button_save),
        showPolicy = false,
        onSend = { _, _, _, _, _, _ ->

        },
        title = stringResource(id = R.string.profile_screen_title)
    )
}