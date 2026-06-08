package com.nursing.admin.service

import com.nursing.admin.dto.*
import com.nursing.admin.entity.Nurse
import com.nursing.admin.repository.NurseRepository
import mu.KotlinLogging
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.time.LocalDateTime

private val logger = KotlinLogging.logger {}

@Service
class NurseService(
    private val nurseRepository: NurseRepository
) {
    
    fun getAllNurses(page: Int = 0, size: Int = 10, status: String? = null): PaginatedResponse<NurseDTO> {
        val pageable = PageRequest.of(page, size)
        
        val nursePage = if (status != null) {
            nurseRepository.findAllByStatus(status, pageable)
        } else {
            nurseRepository.findAll(pageable)
        }
        
        return PaginatedResponse(
            content = nursePage.content.map { it.toDTO() },
            totalElements = nursePage.totalElements,
            totalPages = nursePage.totalPages,
            currentPage = page,
            pageSize = size
        )
    }
    
    fun getNurseById(id: Long): NurseDTO {
        val nurse = nurseRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Nurse not found") }
        return nurse.toDTO()
    }
    
    fun createNurse(request: CreateNurseRequest): NurseDTO {
        if (nurseRepository.findByEmail(request.email) != null) {
            throw IllegalArgumentException("Email already exists")
        }
        
        val nurse = Nurse(
            name = request.name,
            email = request.email,
            phone = request.phone,
            specialization = request.specialization,
            yearsOfExperience = request.yearsOfExperience,
            licenseNumber = request.licenseNumber,
            licenseExpiry = request.licenseExpiry,
            status = "PENDING",
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
        
        val savedNurse = nurseRepository.save(nurse)
        logger.info { "Nurse created: ${savedNurse.id}" }
        
        return savedNurse.toDTO()
    }
    
    fun approveNurse(nurseId: Long, approve: Boolean): NurseDTO {
        val nurse = nurseRepository.findById(nurseId)
            .orElseThrow { IllegalArgumentException("Nurse not found") }
        
        val status = if (approve) "APPROVED" else "REJECTED"
        val updatedNurse = nurse.copy(
            status = status,
            updatedAt = LocalDateTime.now()
        )
        
        val saved = nurseRepository.save(updatedNurse)
        logger.info { "Nurse $nurseId status changed to $status" }
        
        return saved.toDTO()
    }
    
    fun getNursesBySpecialization(specialization: String, page: Int = 0, size: Int = 10): PaginatedResponse<NurseDTO> {
        val pageable = PageRequest.of(page, size)
        val nursePage = nurseRepository.findBySpecializationAndApproved(specialization, pageable)
        
        return PaginatedResponse(
            content = nursePage.content.map { it.toDTO() },
            totalElements = nursePage.totalElements,
            totalPages = nursePage.totalPages,
            currentPage = page,
            pageSize = size
        )
    }
    
    fun getNurseStats(): Map<String, Long> {
        return mapOf(
            "total" to nurseRepository.count(),
            "pending" to nurseRepository.countByStatus("PENDING"),
            "approved" to nurseRepository.countByStatus("APPROVED"),
            "rejected" to nurseRepository.countByStatus("REJECTED"),
            "suspended" to nurseRepository.countByStatus("SUSPENDED")
        )
    }
    
    private fun Nurse.toDTO() = NurseDTO(
        id = id,
        name = name,
        email = email,
        phone = phone,
        specialization = specialization,
        yearsOfExperience = yearsOfExperience,
        licenseNumber = licenseNumber,
        licenseExpiry = licenseExpiry,
        status = status,
        rating = rating,
        totalOrders = totalOrders,
        createdAt = createdAt
    )
}
