package com.nursing.admin.mobile.api

import com.nursing.admin.mobile.data.models.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<ApiResponse<LoginResponse>>

    @GET("auth/health")
    suspend fun health(): Response<Map<String, String>>
}

// Data Models
data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val token: String,
    val admin: AdminDTO
)

data class AdminDTO(
    val id: Long,
    val email: String,
    val name: String,
    val role: String,
    val isActive: Boolean,
    val createdAt: String
)

data class ApiResponse<T>(
    val success: Boolean,
    val message: String,
    val data: T?,
    val timestamp: String
)
