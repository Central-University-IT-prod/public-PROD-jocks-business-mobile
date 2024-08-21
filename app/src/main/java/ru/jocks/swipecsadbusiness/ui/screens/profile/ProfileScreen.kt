package ru.jocks.swipecsadbusiness.ui.screens.profile

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.koin.androidx.compose.getViewModel
import ru.jocks.domain.business.model.BusinessProfileState
import ru.jocks.swipecsadbusiness.R
import ru.jocks.swipecsadbusiness.nav.AppScreens
import ru.jocks.uikit.CompanyProfileCard

@Composable
fun ProfileScreen(navController: NavController) {
    val vm: ProfileViewModel = getViewModel()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { vm.download() }) {
                Icon(painter = painterResource(id = R.drawable.download), contentDescription = null)
            }
        },
        content = {
            LazyColumn(
                modifier = Modifier
                    .padding(16.dp)
                    .padding(it),
                content = {
                    item {
                        when (vm.businessDetails) {
                            is BusinessProfileState.Loading -> {
                                CircularProgressIndicator()
                            }

                            is BusinessProfileState.Error -> {

                            }

                            is BusinessProfileState.Success -> {
                                val info =
                                    (vm.businessDetails as BusinessProfileState.Success).businessProfile
                                CompanyProfileCard(
                                    name = info.name,
                                    rating = info.ratingAverage,
                                    ratingCount = info.ratingCount,
                                    description = info.description,
                                    imageUrl = "http://NOTHING_IS_READY_NOW",
                                    onClick = { /*TODO*/ }
                                )
                            }
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    item {
                        TextButton(
                            onClick = { navController.navigate(AppScreens.EditForm.name) },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(0.dp)
                        ) {
                            Text(text = stringResource(id = R.string.button_edit_form_screen))
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    item {
                        TextButton(
                            onClick = { navController.navigate(AppScreens.AddCoupon.name) },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(0.dp)
                        ) {
                            Text(text = stringResource(id = R.string.button_add_coupon_screen))
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    item {
                        TextButton(
                            onClick = {
                                vm.logout()
                                navController.navigate(AppScreens.Intro.name) {
                                    popUpTo(0)
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(0.dp)
                        ) {
                            Text(text = stringResource(id = R.string.button_logout))
                        }
                    }
                })
        }
    )
}