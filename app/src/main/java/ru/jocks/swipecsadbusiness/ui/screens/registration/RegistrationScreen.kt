package ru.jocks.swipecsadbusiness.ui.screens.registration

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.koin.androidx.compose.getViewModel
import ru.jocks.domain.business.model.BusinessRegisterState
import ru.jocks.swipecsadbusiness.R
import ru.jocks.swipecsadbusiness.nav.Destinations
import ru.jocks.swipecsadbusiness.ui.common.ProfileForm
import timber.log.Timber


@Composable
fun RegistrationScreen(navController: NavController) {
    val vm: RegistrationViewModel = getViewModel()

    val registrationState = vm.registerState

    Column {
        if (registrationState is BusinessRegisterState.Error) {
            Text(
                text = registrationState.message,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(16.dp))
        }

        LaunchedEffect(registrationState) {
            Timber.i("ui registration state $registrationState")

            if (registrationState is BusinessRegisterState.Success) {
                navController.navigate(Destinations.Analytics.route) {
                    popUpTo(0)
                }
            }
        }

        ProfileForm(
            actionName = stringResource(id = R.string.button_register),
            showPolicy = true,
            onSend = { email,
                       name,
                       description,
                       userContactEmail,
                       addresses,
                       password ->
                vm.register(name, description, email, userContactEmail, addresses, password)
            },
            title = stringResource(id = R.string.register_screen_title),
            onBack = { navController.popBackStack() }
        )
    }
}
