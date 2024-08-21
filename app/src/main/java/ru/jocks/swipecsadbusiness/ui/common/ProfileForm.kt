package ru.jocks.swipecsadbusiness.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.getViewModel
import ru.jocks.swipecsadbusiness.R
import ru.jocks.uikit.FormField
import ru.jocks.uikit.TopBarTitle


class RegisterFormValidState(
    emailValidNow: Boolean = false,
    clientEmailContactValidNow: Boolean = false,
    passwordValidNow: Boolean = false,
    nameValidNow: Boolean = false,
    descriptionValidNow: Boolean = false
) {
    private var emailValid: Boolean = emailValidNow
    private var clientEmailContactValid: Boolean = clientEmailContactValidNow
    private var passwordValid: Boolean = passwordValidNow
    private var nameValid: Boolean = nameValidNow
    private var descriptionValid: Boolean = descriptionValidNow

    fun copy(
        emailValidNow: Boolean? = null,
        clientEmailContactValidNow: Boolean? = null,
        passwordValidNow: Boolean? = null,
        nameValidNow: Boolean? = null,
        descriptionValidNow: Boolean? = null,
    ): RegisterFormValidState {
        return RegisterFormValidState(
            emailValidNow = emailValidNow ?: emailValid,
            clientEmailContactValidNow = clientEmailContactValidNow ?: clientEmailContactValid,
            passwordValidNow = passwordValidNow ?: passwordValid,
            nameValidNow = nameValidNow ?: nameValid,
            descriptionValidNow = descriptionValidNow ?: descriptionValid
        )
    }

    fun isValid(): Boolean {
        return emailValid && clientEmailContactValid && passwordValid && nameValid && descriptionValid
    }
}


@Composable
fun AddressField(
    value: String,
    setValue: (String) -> Unit,
    vm: ProfileFormViewModel
) {
    val suggestions = vm.suggestionsState.value
    var dropDownExpanded by remember { mutableStateOf(false) }

    AutoCompleteTextField(
        value = value,
        label = stringResource(id = R.string.hint_address),
        setValue = {
            dropDownExpanded = true
            setValue(it)
            vm.getSuggestions(it)
        },
        onDismissRequest = {
            dropDownExpanded = false
        },
        dropDownExpanded = dropDownExpanded,
        list = suggestions
    )
}


@Composable
fun ProfileForm(
    actionName: String,
    title: String,
    showPolicy: Boolean,
    onSend: (
        email: String,
        name: String,
        description: String,
        userContactEmail: String,
        addresses: List<String>,
        password: String
    ) -> Unit,
    message: String = "",
    onBack: () -> Unit = {}
) {
    val vm: ProfileFormViewModel = getViewModel()
    var email by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var userContactEmail by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var addresses: List<String> by remember {
        mutableStateOf(listOf())
    }

    Scaffold(
        topBar = {
            TopBarTitle(
                title = title,
                onclick = onBack
            )
        },
    ) { padding ->
        var formState by remember {
            mutableStateOf(RegisterFormValidState())
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                content = {
                    item {
                        Text(
                            text = message,
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.Red
                        )
                    }
                    item {
                        FormField(
                            title = stringResource(id = R.string.hint_email),
                            value = email,
                            onValueChange = { it, valid ->
                                email = it
                                formState = formState.copy(emailValidNow = valid)
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$",
                            errorText = stringResource(id = R.string.message_email_invalid)
                        )
                    }
                    item {
                        FormField(
                            title = stringResource(id = R.string.hint_name),
                            value = name,
                            onValueChange = { it, valid ->
                                name = it
                                formState = formState.copy(nameValidNow = valid)
                            },
                            regex = "^[а-яА-ЯёЁa-zA-Z0-9]+$",
                            errorText = stringResource(id = R.string.message_name_invalid)
                        )
                    }
                    item {
                        FormField(
                            title = stringResource(id = R.string.hint_description),
                            value = description,
                            onValueChange = { it, valid ->
                                description = it
                                formState = formState.copy(descriptionValidNow = valid)
                            },
                            regex = "^[а-яА-ЯёЁa-zA-Z0-9 ]+$",
                            errorText = stringResource(id = R.string.message_description_invalid)
                        )
                    }
                    item {
                        FormField(
                            title = stringResource(id = R.string.hint_user_contact_email),
                            value = userContactEmail,
                            onValueChange = { it, valid ->
                                userContactEmail = it
                                formState = formState.copy(clientEmailContactValidNow = valid)
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$",
                            errorText = stringResource(id = R.string.message_email_invalid)
                        )
                    }
                    item {
                        ListForm(
                            startPack = listOf(),
                            fieldForm = { value, onValueChanged ->
                                AddressField(
                                    value = value,
                                    setValue = onValueChanged,
                                    vm = vm
                                )
                            },
                            actionName = "",
                            onClick = {

                            },
                            onValueChange = {
                                addresses = it.map { field ->
                                    field.name
                                }
                            }
                        )
                    }
                    item {
                        FormField(
                            title = stringResource(id = R.string.hint_password),
                            value = password,
                            onValueChange = { it, valid ->
                                password = it
                                formState = formState.copy(passwordValidNow = valid)
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            visualTransformation = PasswordVisualTransformation(),
                            errorText = stringResource(id = R.string.message_password_requirements),
                            regex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}\$"
                        )
                    }
                    item {
                        if (showPolicy) {
                            val agreeTitleText = buildAnnotatedString {
                                append(stringResource(id = R.string.agree_with_policy_title_first_part))

                                append(" ")

                                pushStringAnnotation(
                                    tag = "policy",
                                    annotation = "https://test.test/some_policy"
                                )
                                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                                    append(stringResource(id = R.string.agree_with_policy_title_second_part))
                                }
                                pop()
                            }

                            val uriHandler = LocalUriHandler.current

                            ClickableText(
                                text = agreeTitleText,
                                style = MaterialTheme.typography.displaySmall,
                                onClick = { offset ->
                                    agreeTitleText.getStringAnnotations(
                                        tag = "policy",
                                        start = offset,
                                        end = offset
                                    ).firstOrNull()?.let {
                                        uriHandler.openUri(it.item)
                                    }
                                },
                            )

                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }

                    item {
                        Button(
                            onClick = {
                                onSend(
                                    email,
                                    name,
                                    description,
                                    userContactEmail,
                                    addresses,
                                    password
                                )
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6B4EFF)),
                            enabled = formState.isValid()
                        )
                        {
                            Text(
                                text = actionName,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            )
        }
    }
}
