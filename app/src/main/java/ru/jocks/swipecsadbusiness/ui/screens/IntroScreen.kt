package ru.jocks.swipecsadbusiness.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.jocks.swipecsadbusiness.R
import ru.jocks.swipecsadbusiness.nav.AppScreens
import ru.jocks.uikit.CustomButton

@Composable
fun IntroScreen(navController: NavController) {
    Surface(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {
            item {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color(0xFF090A0A))) {
                            append(stringResource(id = R.string.app_name_first_part))
                        }

                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF6B4EFF)
                            )
                        ) {
                            append(stringResource(id = R.string.app_name_second_part))
                        }

                        append(" ")

                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF090A0A),
                                fontStyle = FontStyle.Italic
                            )
                        ) {
                            append(stringResource(id = R.string.app_name_business_part))
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center
                )
            }

            item {
                Spacer(modifier = Modifier.height(55.dp))
            }

            item {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.image_placeholder),
                        contentDescription = "",
                        modifier = Modifier
                            .height(177.dp)
                            .width(213.dp)
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(55.dp))
            }

            item {
                Text(
                    text = stringResource(id = R.string.intro_text),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineLarge,
                )
            }

            item {
                Spacer(modifier = Modifier.height(55.dp))
            }

            item {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CustomButton(
                        text = stringResource(id = R.string.button_start_register)
                    ) {
                        navController.navigate(AppScreens.Registration.name)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(
                    onClick = {
                        navController.navigate(AppScreens.Login.name)
                    }
                ) {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(color = Color(0xFF202325))) {
                                append(stringResource(id = R.string.button_login_first_part))
                            }

                            append(" ")

                            withStyle(style = SpanStyle(color = Color(0xFF6B4EFF))) {
                                append(stringResource(id = R.string.button_login_second_part))
                            }
                        },
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
    }
}
