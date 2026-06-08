package com.nursing.admin.controller

import com.nursing.admin.dto.ApiResponse
import com.nursing.admin.dto.CreatePatientRequest
import com.nursing.admin.dto.PatientDTO
import com.nursing.admin.dto.UpdatePatientRequest
import com.nursing.admin.service.PatientService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/patients")
@CrossOrigin("*")
class PatientController(
    private val patientService: PatientService
) {
    
    @GetMapping
    fun getAllPatients(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(required = false) status: String?
    ): ResponseEntity<ApiResponse<Any>> {
        return try {
            val patients = patientService.getAllPatients(page, size, status)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Patients retrieved successfully",
                    data = patients
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to retrieve patients",
                    data = null
                )
            )
        }
    }
    
    @GetMapping("/{id}")
    fun getPatientById(@PathVariable id: Long): ResponseEntity<ApiResponse<PatientDTO>> {
        return try {
            val patient = patientService.getPatientById(id)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Patient retrieved successfully",
                    data = patient
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Patient not found",
                    data = null
                )
            )
        }
    }
    
    @PostMapping
    fun createPatient(@RequestBody request: CreatePatientRequest): ResponseEntity<ApiResponse<PatientDTO>> {
        return try {
            val patient = patientService.createPatient(request)
            ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse(
                    success = true,
                    message = "Patient created successfully",
                    data = patient
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to create patient",
                    data = null
                )
            )
        }
    }
    
    @PutMapping("/{id}")
    fun updatePatient(
        @PathVariable id: Long,
        @RequestBody request: UpdatePatientRequest
    ): ResponseEntity<ApiResponse<PatientDTO>> {
        return try {
            val patient = patientService.updatePatient(id, request)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Patient updated successfully",
                    data = patient
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to update patient",
                    data = null
                )
            )
        }
    }
    
    @DeleteMapping("/{id}")
    fun deletePatient(@PathVariable id: Long): ResponseEntity<ApiResponse<String>> {
        return try {
            patientService.deletePatient(id)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Patient deleted successfully",
                    data = "Patient $id deleted"
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to delete patient",
                    data = null
                )
            )
        }
    }
    
    @GetMapping("/stats")
    fun getPatientStats(): ResponseEntity<ApiResponse<Map<String, Long>>> {
        return try {
            val stats = patientService.getPatientStats()
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Patient statistics retrieved successfully",
                    data = stats
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to retrieve statistics",
                    data = null
                )
            )
        }
    }
}
