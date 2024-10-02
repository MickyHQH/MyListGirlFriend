package com.haquanghuy.socialdemo.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.haquanghuy.socialdemo.ui.navigation.AppNavHost

@Composable
fun AppScreen() {
    val navController = rememberNavController()
    AppNavHost(navController = navController)
}
