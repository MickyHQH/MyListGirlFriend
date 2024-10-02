package com.haquanghuy.socialdemo.ui.navigation

sealed class AppRouter(val route: String) {
    data object OnBoard : AppRouter("onboard")
    data object Home : AppRouter("home")
    data object UserDetail : AppRouter("user/{id}") {
        fun pushParam(id: Int): String {
            return "user/$id";
        }
    }
}