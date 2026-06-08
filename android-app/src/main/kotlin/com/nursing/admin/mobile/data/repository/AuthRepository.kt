package com.nursing.admin.mobile.data.repository

import com.nursing.admin.mobile.api.ApiClient
import com.nursing.admin.mobile.api.AuthService
import com.nursing.admin.mobile.api.LoginRequest
import com.nursing.admin.mobile.utils.TokenManager

class AuthRepository(private val tokenManager: TokenManager) {
    private val authService = ApiClient.createService(AuthService::class.java)

    suspend fun login(email: String, password: String): Result<String> {
        return try {
            val response = authService.login(LoginRequest(email, password))
            if (response.isSuccessful && response.body()?.success == true) {
                val loginResponse = response.body()?.data
                if (loginResponse != null) {
                    tokenManager.saveToken(loginResponse.token)
                    tokenManager.saveAdminInfo(
                        loginResponse.admin.id.toString(),
                        loginResponse.admin.email,
                        loginResponse.admin.name
                    )
                    Result.success(loginResponse.token)
                } else {
                    Result.failure(Exception("Invalid response"))
                }
            } else {
                Result.failure(Exception(response.body()?.message ?: "Login failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun logout() {
        tokenManager.clearAll()
    }

    fun getTokenFlow() = tokenManager.getTokenFlow()
    fun getAdminNameFlow() = tokenManager.getAdminNameFlow()
    fun getAdminEmailFlow() = tokenManager.getAdminEmailFlow()
}
