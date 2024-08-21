package ru.jocks.swipecsadbusiness.ui.screens.editcoupon

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.getViewModel
import ru.jocks.swipecsadbusiness.R
import ru.jocks.uikit.CustomButton
import ru.jocks.uikit.FormField


class AddCouponFormValidState(
    private var couponNameValid: Boolean = false,
    private var couponTextValid: Boolean = false,
) {

    fun copy(
        couponNameValidNow: Boolean? = null,
        couponTextValidNow: Boolean? = null,
    ): AddCouponFormValidState {
        return AddCouponFormValidState(
            couponNameValid = couponNameValidNow ?: couponNameValid,
            couponTextValid = couponTextValidNow ?: couponTextValid,
        )
    }

    fun isValid(): Boolean {
        return couponTextValid and couponNameValid
    }
}

@Composable
fun DashDivider(color: Color) {
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    Canvas(
        Modifier
            .fillMaxWidth()
            .height(1.dp)
    ) {

        drawLine(
            color = color,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            pathEffect = pathEffect,
            strokeWidth = 5f
        )
    }
}

@Composable
fun CouponAddScreen() {
    val vm: CouponEditViewModel = getViewModel()

    var couponTitle by remember {
        mutableStateOf("")
    }
    var couponText by remember {
        mutableStateOf("")
    }
    var formState by remember {
        mutableStateOf(AddCouponFormValidState())
    }

    Box {
        Column(
            Modifier.padding(24.dp)
        ) {
            FormField(
                title = stringResource(id = R.string.hint_coupon_name),
                value = couponTitle,
                onValueChange = { it, valid ->
                    couponTitle = it
                    formState = formState.copy(couponNameValidNow = valid)
                },
                regex = ".+",
                errorText = stringResource(id = R.string.message_coupon_name_invalid)
            )
            FormField(
                title = stringResource(id = R.string.hint_coupon_text),
                value = couponText,
                onValueChange = { it, valid ->
                    couponText = it
                    formState = formState.copy(couponTextValidNow = valid)
                },
                regex = ".+",
                errorText = stringResource(id = R.string.message_coupon_text_invalid)
            )

            Spacer(Modifier.height(110.dp))

            ElevatedCard(
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                content = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp)
                            .padding(horizontal = 24.dp),
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            style = MaterialTheme.typography.headlineMedium,
                            text = couponTitle,
                        )
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = couponText,
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    DashDivider(color = Color.Black)

                    Spacer(modifier = Modifier.height(8.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        content = {
                            Text(
                                text = stringResource(id = R.string.coupon_title),
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Right,
                                style = MaterialTheme.typography.labelMedium
                            )
                            Text(
                                text = stringResource(id = R.string.coupon_test_token),
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Right,
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                    )
                }
            )

            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center
            ) {
                CustomButton(
                    text = stringResource(id = R.string.button_save),
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    enabled = formState.isValid(),
                ) {
                    vm.updateCoupon(
                        text = couponTitle,
                        description = couponText
                    )
                }
            }
        }
    }
}