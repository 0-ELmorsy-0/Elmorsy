package com.nursing.admin.service

import com.nursing.admin.dto.*
import com.nursing.admin.entity.Patient
import com.nursing.admin.repository.PatientRepository
import mu.KotlinLogging
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.time.LocalDateTime

private val logger = KotlinLogging.logger {}

@Service
class PatientService(
    private val patientRepository: PatientRepository
) {
    
    fun getAllPatients(page: Int = 0, size: Int = 10, status: String? = null): PaginatedResponse<PatientDTO> {
        val pageable = PageRequest.of(page, size)
        
        val patientPage = if (status != null) {
            patientRepository.findAllByStatus(status, pageable)
        } else {
            patientRepository.findAll(pageable)
        }
        
        return PaginatedResponse(
            content = patientPage.content.map { it.toDTO() },
            totalElements = patientPage.totalElements,
            totalPages = patientPage.totalPages,
            currentPage = page,
            pageSize = size
        )
    }
    
    fun getPatientById(id: Long): PatientDTO {
        val patient = patientRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Patient not found") }
        return patient.toDTO()
    }
    
    fun createPatient(request: CreatePatientRequest): PatientDTO {
        if (patientRepository.findByEmail(request.email) != null) {
            throw IllegalArgumentException("Email already exists")
        }
        
        val patient = Patient(
            name = request.name,
            email = request.email,
            phone = request.phone,
            address = request.address,
            age = request.age,
            medicalHistory = request.medicalHistory,
            status = "ACTIVE",
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
        
        val savedPatient = patientRepository.save(patient)
        logger.info { "Patient created: ${savedPatient.id}" }
        
        return savedPatient.toDTO()
    }
    
    fun updatePatient(id: Long, request: UpdatePatientRequest): PatientDTO {
        val patient = patientRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Patient not found") }
        
        val updatedPatient = patient.copy(
            name = request.name ?: patient.name,
            phone = request.phone ?: patient.phone,
            address = request.address ?: patient.address,
            age = request.age ?: patient.age,
            medicalHistory = request.medicalHistory ?: patient.medicalHistory,
            status = request.status ?: patient.status,
            updatedAt = LocalDateTime.now()
        )
        
        val saved = patientRepository.save(updatedPatient)
        logger.info { "Patient updated: $id" }
        
        return saved.toDTO()
    }
    
    fun deletePatient(id: Long) {
        if (!patientRepository.existsById(id)) {
            throw IllegalArgumentException("Patient not found")
        }
        patientRepository.deleteById(id)
        logger.info { "Patient deleted: $id" }
    }
    
    fun getPatientStats(): Map<String, Long> {
        return mapOf(
            "total" to patientRepository.count(),
            "active" to patientRepository.countByStatus("ACTIVE"),
            "inactive" to patientRepository.countByStatus("INACTIVE"),
            "suspended" to patientRepository.countByStatus("SUSPENDED")
        )
    }
    
    private fun Patient.toDTO() = PatientDTO(
        id = id,
        name = name,
        email = email,
        phone = phone,
        address = address,
        age = age,
        medicalHistory = medicalHistory,
        status = status,
        createdAt = createdAt
    )
}
