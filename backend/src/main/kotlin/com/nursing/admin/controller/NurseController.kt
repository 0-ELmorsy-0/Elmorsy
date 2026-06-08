package com.nursing.admin.controller

import com.nursing.admin.dto.ApiResponse
import com.nursing.admin.dto.ApproveNurseRequest
import com.nursing.admin.dto.CreateNurseRequest
import com.nursing.admin.dto.NurseDTO
import com.nursing.admin.service.NurseService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/nurses")
@CrossOrigin("*")
class NurseController(
    private val nurseService: NurseService
) {
    
    @GetMapping
    fun getAllNurses(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(required = false) status: String?
    ): ResponseEntity<ApiResponse<Any>> {
        return try {
            val nurses = nurseService.getAllNurses(page, size, status)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Nurses retrieved successfully",
                    data = nurses
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to retrieve nurses",
                    data = null
                )
            )
        }
    }
    
    @GetMapping("/{id}")
    fun getNurseById(@PathVariable id: Long): ResponseEntity<ApiResponse<NurseDTO>> {
        return try {
            val nurse = nurseService.getNurseById(id)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Nurse retrieved successfully",
                    data = nurse
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Nurse not found",
                    data = null
                )
            )
        }
    }
    
    @PostMapping
    fun createNurse(@RequestBody request: CreateNurseRequest): ResponseEntity<ApiResponse<NurseDTO>> {
        return try {
            val nurse = nurseService.createNurse(request)
            ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse(
                    success = true,
                    message = "Nurse created successfully",
                    data = nurse
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to create nurse",
                    data = null
                )
            )
        }
    }
    
    @PutMapping("/{id}/approve")
    fun approveNurse(
        @PathVariable id: Long,
        @RequestBody request: ApproveNurseRequest
    ): ResponseEntity<ApiResponse<NurseDTO>> {
        return try {
            val nurse = nurseService.approveNurse(id, request.approve)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Nurse approval updated successfully",
                    data = nurse
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to update nurse approval",
                    data = null
                )
            )
        }
    }
    
    @GetMapping("/specialization/{specialization}")
    fun getNursesBySpecialization(
        @PathVariable specialization: String,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<ApiResponse<Any>> {
        return try {
            val nurses = nurseService.getNursesBySpecialization(specialization, page, size)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Nurses retrieved successfully",
                    data = nurses
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to retrieve nurses",
                    data = null
                )
            )
        }
    }
    
    @GetMapping("/stats")
    fun getNurseStats(): ResponseEntity<ApiResponse<Map<String, Long>>> {
        return try {
            val stats = nurseService.getNurseStats()
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Nurse statistics retrieved successfully",
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
