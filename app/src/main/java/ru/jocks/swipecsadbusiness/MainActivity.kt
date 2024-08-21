package ru.jocks.swipecsadbusiness

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.koin.java.KoinJavaComponent
import ru.jocks.domain.business.repository.BusinessRepository
import ru.jocks.swipecsadbusiness.nav.AppScreens
import ru.jocks.swipecsadbusiness.nav.Destinations
import ru.jocks.swipecsadbusiness.ui.common.BottomNavigationBar
import ru.jocks.swipecsadbusiness.ui.screens.analytics.AnalyticScreen
import ru.jocks.swipecsadbusiness.ui.screens.editcoupon.CouponAddScreen
import ru.jocks.swipecsadbusiness.ui.screens.editform.EditFormScreen
import ru.jocks.swipecsadbusiness.ui.screens.feebacks.FeedbacksScreen
import ru.jocks.swipecsadbusiness.ui.screens.IntroScreen
import ru.jocks.swipecsadbusiness.ui.screens.profile.ProfileScreen
import ru.jocks.swipecsadbusiness.ui.screens.login.LoginScreen
import ru.jocks.swipecsadbusiness.ui.screens.registration.RegistrationScreen
import ru.jocks.swipecsadbusiness.ui.theme.SwipeCSADBusinessTheme
import timber.log.Timber

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SwipeCSADBusinessTheme {
                Scaffold (
                    content = { padding ->
                        Box(
                           modifier = Modifier.padding(padding)
                        ) {
                            HomeScreen(
                                rememberNavController()
                            )
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun HomeScreen(navController: NavHostController) {
    val businessRepository : BusinessRepository by KoinJavaComponent.inject(BusinessRepository::class.java)
    Timber.i("businessRepository $businessRepository")

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            if (currentDestination?.route !in AppScreens.entries.map { it.name } ) {
                BottomNavigationBar(navController = navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = if (businessRepository.getSavedBusinesses()!=null) { Destinations.Analytics.route } else { AppScreens.Intro.name },
            Modifier.padding(innerPadding)
        ) {
            composable(AppScreens.Registration.name) { RegistrationScreen(navController) }
            composable(AppScreens.Login.name) { LoginScreen(navController) }
            composable(AppScreens.Intro.name) { IntroScreen(navController) }
            composable(AppScreens.AddCoupon.name) { CouponAddScreen() }
            composable(AppScreens.EditForm.name) { EditFormScreen() }
            composable(Destinations.Analytics.route) { AnalyticScreen() }
            composable(Destinations.Feedbacks.route) { FeedbacksScreen() }
            composable(Destinations.Settings.route) { ProfileScreen(navController) }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SwipeCSADBusinessTheme {
        Greeting("Android")
    }
}