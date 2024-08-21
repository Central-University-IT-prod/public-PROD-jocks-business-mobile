package ru.jocks.swipecsadbusiness.ui.screens.login

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.koin.androidx.compose.getViewModel
import ru.jocks.domain.business.model.BusinessLoginState
import ru.jocks.swipecsadbusiness.R
import ru.jocks.swipecsadbusiness.nav.Destinations
import ru.jocks.uikit.FormField
import ru.jocks.uikit.TopBarTitle
import timber.log.Timber


class LoginFormValidState (
    var emailValid: Boolean = false,
    var passwordValid: Boolean = false
){
    init {
        Timber.tag("LoginScreen").d("Init login form state, $emailValid, $passwordValid")
    }
    fun isValid(): Boolean {
        Timber.tag("LoginScreen").d("Check valid $emailValid, $passwordValid")
        return emailValid && passwordValid
    }
}


@Composable
fun LoginScreen(navController: NavController) {
    val vm: LoginViewModel = getViewModel()
    val loginState = vm.loginState

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    LaunchedEffect(loginState) {
        when (loginState) {
            is BusinessLoginState.Error -> {
                Toast.makeText(context.applicationContext, loginState.message, Toast.LENGTH_SHORT).show()
            }
            is BusinessLoginState.Success -> {
                navController.navigate(Destinations.Analytics.route) {
                    popUpTo(0)
                }
            }

            else -> {}
        }
    }



    Scaffold(
        topBar = {
            TopBarTitle(
                title = stringResource(id = R.string.login_screen_title),
                onclick = {
                    navController.popBackStack()
                }
            )
        },
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            var formState by remember {
                mutableStateOf(LoginFormValidState())
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                content = {
                    item {
                        FormField(
                            title = stringResource(id = R.string.hint_email),
                            value = email,
                            onValueChange = { it, valid ->
                                email = it
                                Timber.tag("LoginScreen").d("Email: $email : $valid")
                                formState = LoginFormValidState(valid, formState.passwordValid)
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$",
                            errorText = stringResource(id = R.string.message_email_invalid)
                        )
                    }
                    item {
                        FormField(
                            title = stringResource(id = R.string.hint_password),
                            value = password,
                            onValueChange = { it, valid ->
                                password = it
                                formState = LoginFormValidState(formState.emailValid, valid)
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            visualTransformation = PasswordVisualTransformation(),
                            regex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
                            errorText = stringResource(id = R.string.message_password_requirements)
                        )
                    }
                }
            )

            Box(
                modifier = Modifier.padding(20.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Button(
                        onClick = {
                            Timber.tag("LoginScreen").d("$email, $password")
                            vm.login(
                                email,
                                password
                            )
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6B4EFF)),
                        enabled = formState.isValid()
                    )
                    {
                        Text(
                            text = stringResource(id = R.string.button_login),
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}
