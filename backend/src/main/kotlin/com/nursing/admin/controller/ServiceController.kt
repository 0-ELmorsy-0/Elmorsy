package com.nursing.admin.controller

import com.nursing.admin.dto.ApiResponse
import com.nursing.admin.dto.CreateServiceRequest
import com.nursing.admin.dto.ServiceDTO
import com.nursing.admin.service.ServiceManagementService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/services")
@CrossOrigin("*")
class ServiceController(
    private val serviceManagementService: ServiceManagementService
) {
    
    @GetMapping
    fun getAllServices(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "true") isActive: Boolean
    ): ResponseEntity<ApiResponse<Any>> {
        return try {
            val services = serviceManagementService.getAllServices(page, size, isActive)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Services retrieved successfully",
                    data = services
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to retrieve services",
                    data = null
                )
            )
        }
    }
    
    @GetMapping("/{id}")
    fun getServiceById(@PathVariable id: Long): ResponseEntity<ApiResponse<ServiceDTO>> {
        return try {
            val service = serviceManagementService.getServiceById(id)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Service retrieved successfully",
                    data = service
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Service not found",
                    data = null
                )
            )
        }
    }
    
    @PostMapping
    fun createService(@RequestBody request: CreateServiceRequest): ResponseEntity<ApiResponse<ServiceDTO>> {
        return try {
            val service = serviceManagementService.createService(request)
            ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse(
                    success = true,
                    message = "Service created successfully",
                    data = service
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to create service",
                    data = null
                )
            )
        }
    }
    
    @PutMapping("/{id}")
    fun updateService(
        @PathVariable id: Long,
        @RequestBody request: CreateServiceRequest
    ): ResponseEntity<ApiResponse<ServiceDTO>> {
        return try {
            val service = serviceManagementService.updateService(id, request)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Service updated successfully",
                    data = service
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to update service",
                    data = null
                )
            )
        }
    }
    
    @DeleteMapping("/{id}")
    fun deleteService(@PathVariable id: Long): ResponseEntity<ApiResponse<String>> {
        return try {
            serviceManagementService.deleteService(id)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Service deleted successfully",
                    data = "Service $id deleted"
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to delete service",
                    data = null
                )
            )
        }
    }
    
    @PutMapping("/{id}/toggle-status")
    fun toggleServiceStatus(@PathVariable id: Long): ResponseEntity<ApiResponse<ServiceDTO>> {
        return try {
            val service = serviceManagementService.toggleServiceStatus(id)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Service status toggled successfully",
                    data = service
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to toggle service status",
                    data = null
                )
            )
        }
    }
}
