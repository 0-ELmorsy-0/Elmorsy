package com.nursing.admin.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.nursing.admin.mobile.api.ApiClient
import com.nursing.admin.mobile.data.repository.AuthRepository
import com.nursing.admin.mobile.ui.screens.DashboardScreen
import com.nursing.admin.mobile.ui.screens.LoginScreen
import com.nursing.admin.mobile.ui.theme.NursingAdminTheme
import com.nursing.admin.mobile.ui.viewmodel.AuthViewModel
import com.nursing.admin.mobile.utils.TokenManager

class MainActivity : ComponentActivity() {
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize API Client
        ApiClient.initialize(this)

        // Initialize TokenManager and Repository
        val tokenManager = TokenManager(this)
        val authRepository = AuthRepository(tokenManager)

        // Create ViewModel
        authViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                return AuthViewModel(authRepository) as T
            }
        }).get(AuthViewModel::class.java)

        setContent {
            NursingAdminTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val uiState by authViewModel.uiState.collectAsState()

                    if (uiState.isLoggedIn) {
                        DashboardScreen(
                            onLogout = {
                                authViewModel.logout()
                            },
                            adminName = uiState.adminName
                        )
                    } else {
                        LoginScreen(
                            viewModel = authViewModel,
                            onLoginSuccess = {
                                // Navigation handled by state
                            }
                        )
                    }
                }
            }
        }
    }
}
