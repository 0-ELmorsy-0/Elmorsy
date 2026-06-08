package com.nursing.admin.mobile.ui.navigation

sealed class Screen {
    object Login : Screen()
    object Dashboard : Screen()
    object Patients : Screen()
    object Nurses : Screen()
    object Orders : Screen()
    object Analytics : Screen()
    object Payments : Screen()
}

class NavigationManager {
    private var currentScreen: Screen = Screen.Login

    fun navigate(screen: Screen) {
        currentScreen = screen
    }

    fun goBack(): Boolean {
        return when (currentScreen) {
            Screen.Login -> false
            Screen.Dashboard -> {
                currentScreen = Screen.Dashboard
                false
            }
            else -> {
                currentScreen = Screen.Dashboard
                true
            }
        }
    }

    fun getCurrentScreen(): Screen = currentScreen
}
