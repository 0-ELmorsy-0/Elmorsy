package com.nursing.admin.controller

import com.nursing.admin.dto.ApiResponse
import com.nursing.admin.dto.ComplaintDTO
import com.nursing.admin.dto.CreateComplaintRequest
import com.nursing.admin.dto.ResolveComplaintRequest
import com.nursing.admin.service.ComplaintService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/complaints")
@CrossOrigin("*")
class ComplaintController(
    private val complaintService: ComplaintService
) {
    
    @GetMapping
    fun getAllComplaints(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(required = false) status: String?,
        @RequestParam(required = false) priority: String?
    ): ResponseEntity<ApiResponse<Any>> {
        return try {
            val complaints = complaintService.getAllComplaints(page, size, status, priority)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Complaints retrieved successfully",
                    data = complaints
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to retrieve complaints",
                    data = null
                )
            )
        }
    }
    
    @GetMapping("/{id}")
    fun getComplaintById(@PathVariable id: Long): ResponseEntity<ApiResponse<ComplaintDTO>> {
        return try {
            val complaint = complaintService.getComplaintById(id)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Complaint retrieved successfully",
                    data = complaint
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Complaint not found",
                    data = null
                )
            )
        }
    }
    
    @PostMapping
    fun createComplaint(@RequestBody request: CreateComplaintRequest): ResponseEntity<ApiResponse<ComplaintDTO>> {
        return try {
            val complaint = complaintService.createComplaint(request)
            ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse(
                    success = true,
                    message = "Complaint created successfully",
                    data = complaint
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to create complaint",
                    data = null
                )
            )
        }
    }
    
    @PutMapping("/{id}/resolve")
    fun resolveComplaint(
        @PathVariable id: Long,
        @RequestBody request: ResolveComplaintRequest
    ): ResponseEntity<ApiResponse<ComplaintDTO>> {
        return try {
            val complaint = complaintService.resolveComplaint(id, request.resolution, request.status)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Complaint resolved successfully",
                    data = complaint
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to resolve complaint",
                    data = null
                )
            )
        }
    }
    
    @GetMapping("/stats")
    fun getComplaintStats(): ResponseEntity<ApiResponse<Map<String, Long>>> {
        return try {
            val stats = complaintService.getComplaintStats()
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Complaint statistics retrieved successfully",
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
