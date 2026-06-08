package com.nursing.admin.service

import com.nursing.admin.dto.AdminDTO
import com.nursing.admin.dto.LoginRequest
import com.nursing.admin.dto.LoginResponse
import com.nursing.admin.entity.Admin
import com.nursing.admin.repository.AdminRepository
import com.nursing.admin.security.JwtTokenProvider
import mu.KotlinLogging
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime

private val logger = KotlinLogging.logger {}

@Service
class AuthService(
    private val adminRepository: AdminRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider
) {
    
    fun login(request: LoginRequest): LoginResponse {
        val admin = adminRepository.findByEmail(request.email)
            ?: throw IllegalArgumentException("Invalid email or password")
        
        if (!admin.isActive) {
            throw IllegalArgumentException("Admin account is inactive")
        }
        
        if (!passwordEncoder.matches(request.password, admin.password)) {
            throw IllegalArgumentException("Invalid email or password")
        }
        
        val token = jwtTokenProvider.generateToken(admin.id)
        
        logger.info { "Admin ${admin.email} logged in successfully" }
        
        return LoginResponse(
            token = token,
            admin = admin.toDTO()
        )
    }
    
    fun createAdmin(email: String, password: String, name: String, role: String = "ADMIN"): Admin {
        if (adminRepository.existsByEmail(email)) {
            throw IllegalArgumentException("Email already exists")
        }
        
        val admin = Admin(
            email = email,
            password = passwordEncoder.encode(password),
            name = name,
            role = role,
            isActive = true,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
        
        return adminRepository.save(admin)
    }
    
    fun getAdminById(id: Long): Admin {
        return adminRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Admin not found") }
    }
    
    private fun Admin.toDTO() = AdminDTO(
        id = id,
        email = email,
        name = name,
        role = role,
        isActive = isActive,
        createdAt = createdAt
    )
}
