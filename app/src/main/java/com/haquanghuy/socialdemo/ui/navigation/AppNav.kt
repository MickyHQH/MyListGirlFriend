package com.haquanghuy.socialdemo.ui.navigation

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.haquanghuy.socialdemo.ui.home.HomeScreen
import com.haquanghuy.socialdemo.ui.onboard.OnboardScreen
import com.haquanghuy.socialdemo.ui.userdetail.UserDetailScreen

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    fun onBack() {
        navController.navigateUp()
//        navController.popBackStack()
    }
    NavHost(
        navController = navController,
        startDestination = AppRouter.OnBoard.route,
        modifier = modifier,
    ) {
        composable(
            route = AppRouter.OnBoard.route,
        ) {
            OnboardScreen {
                navController.navigate(AppRouter.Home.route)
            }
        }
        composable(
            route = AppRouter.Home.route,
        ) {
            HomeScreen(
                onGoToUserDetail = {
                    navController.navigate(AppRouter.UserDetail.pushParam(it))
                }
            ) {
                onBack()
            }
        }
        composable(
            route = AppRouter.UserDetail.route,
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                },
            ),
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "myapp://haquanghuy.com/user/{id}"
                    action = Intent.ACTION_VIEW
                }
            ),
        ) {
            val id = it.arguments?.getInt("id")
            UserDetailScreen(userId = id) {
                onBack()
            }
        }
    }
}