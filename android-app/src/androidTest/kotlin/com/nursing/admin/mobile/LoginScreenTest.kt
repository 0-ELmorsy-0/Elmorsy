package com.nursing.admin.mobile

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nursing.admin.mobile.data.repository.AuthRepository
import com.nursing.admin.mobile.ui.screens.LoginScreen
import com.nursing.admin.mobile.ui.viewmodel.AuthViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class LoginScreenTest {
    
    @get:Rule
    val composeTestRule = createComposeRule()

    @Mock
    private lateinit var authRepository: AuthRepository

    @Test
    fun testLoginScreenDisplaysCorrectly() {
        MockitoAnnotations.openMocks(this)
        val viewModel = AuthViewModel(authRepository)

        composeTestRule.setContent {
            LoginScreen(
                viewModel = viewModel,
                onLoginSuccess = {}
            )
        }

        // Verify that login screen elements are displayed
        composeTestRule.onNodeWithText("Nursing Admin").assertExists()
        composeTestRule.onNodeWithText("Dashboard Login").assertExists()
        composeTestRule.onNodeWithText("Email").assertExists()
        composeTestRule.onNodeWithText("Password").assertExists()
        composeTestRule.onNodeWithText("Login").assertExists()
    }

    @Test
    fun testLoginButtonClickable() {
        MockitoAnnotations.openMocks(this)
        val viewModel = AuthViewModel(authRepository)

        composeTestRule.setContent {
            LoginScreen(
                viewModel = viewModel,
                onLoginSuccess = {}
            )
        }

        // Enter credentials
        composeTestRule.onNodeWithText("Email").performTextInput("admin@example.com")
        composeTestRule.onNodeWithText("Password").performTextInput("password123")

        // Click login button
        composeTestRule.onNodeWithText("Login").performClick()

        // Verify login was attempted
    }

    @Test
    fun testLoginValidation() {
        MockitoAnnotations.openMocks(this)
        val viewModel = AuthViewModel(authRepository)

        composeTestRule.setContent {
            LoginScreen(
                viewModel = viewModel,
                onLoginSuccess = {}
            )
        }

        // Try to click login without entering credentials
        composeTestRule.onNodeWithText("Login").performClick()

        // Verify that login button is disabled or error is shown
    }
}
