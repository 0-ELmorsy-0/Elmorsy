package com.nursing.admin.controller

import com.nursing.admin.dto.ApiResponse
import com.nursing.admin.dto.ApproveCommissionRequest
import com.nursing.admin.dto.CommissionDTO
import com.nursing.admin.service.CommissionService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/commissions")
@CrossOrigin("*")
class CommissionController(
    private val commissionService: CommissionService
) {
    
    @GetMapping
    fun getAllCommissions(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(required = false) status: String?
    ): ResponseEntity<ApiResponse<Any>> {
        return try {
            val commissions = commissionService.getAllCommissions(page, size, status)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Commissions retrieved successfully",
                    data = commissions
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to retrieve commissions",
                    data = null
                )
            )
        }
    }
    
    @GetMapping("/{id}")
    fun getCommissionById(@PathVariable id: Long): ResponseEntity<ApiResponse<CommissionDTO>> {
        return try {
            val commission = commissionService.getCommissionById(id)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Commission retrieved successfully",
                    data = commission
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Commission not found",
                    data = null
                )
            )
        }
    }
    
    @GetMapping("/nurse/{nurseId}")
    fun getNurseCommissions(
        @PathVariable nurseId: Long,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<ApiResponse<Any>> {
        return try {
            val commissions = commissionService.getNurseCommissions(nurseId, page, size)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Nurse commissions retrieved successfully",
                    data = commissions
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to retrieve commissions",
                    data = null
                )
            )
        }
    }
    
    @PutMapping("/{id}/approve")
    fun approveCommission(
        @PathVariable id: Long,
        @RequestBody request: ApproveCommissionRequest
    ): ResponseEntity<ApiResponse<CommissionDTO>> {
        return try {
            val commission = commissionService.approveCommission(id, request.approve)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Commission approval updated successfully",
                    data = commission
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to update commission approval",
                    data = null
                )
            )
        }
    }
    
    @GetMapping("/nurse/{nurseId}/total")
    fun getTotalNurseCommission(@PathVariable nurseId: Long): ResponseEntity<ApiResponse<Map<String, Any>>> {
        return try {
            val total = commissionService.getTotalNurseCommission(nurseId)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Total commission retrieved successfully",
                    data = mapOf("nurseId" to nurseId, "totalCommission" to total)
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to retrieve commission",
                    data = null
                )
            )
        }
    }
    
    @GetMapping("/stats")
    fun getCommissionStats(): ResponseEntity<ApiResponse<Map<String, Any>>> {
        return try {
            val stats = commissionService.getCommissionStats()
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Commission statistics retrieved successfully",
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
