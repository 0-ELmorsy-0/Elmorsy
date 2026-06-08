package com.nursing.admin.service

import com.nursing.admin.dto.CreateServiceRequest
import com.nursing.admin.dto.PaginatedResponse
import com.nursing.admin.dto.ServiceDTO
import com.nursing.admin.entity.Service
import com.nursing.admin.repository.ServiceRepository
import mu.KotlinLogging
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service as SpringService
import java.time.LocalDateTime

private val logger = KotlinLogging.logger {}

@SpringService
class ServiceManagementService(
    private val serviceRepository: ServiceRepository
) {
    
    fun getAllServices(page: Int = 0, size: Int = 10, isActive: Boolean = true): PaginatedResponse<ServiceDTO> {
        val pageable = PageRequest.of(page, size)
        val servicePage = serviceRepository.findAllByIsActive(isActive, pageable)
        
        return PaginatedResponse(
            content = servicePage.content.map { it.toDTO() },
            totalElements = servicePage.totalElements,
            totalPages = servicePage.totalPages,
            currentPage = page,
            pageSize = size
        )
    }
    
    fun getServiceById(id: Long): ServiceDTO {
        val service = serviceRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Service not found") }
        return service.toDTO()
    }
    
    fun createService(request: CreateServiceRequest): ServiceDTO {
        val service = Service(
            name = request.name,
            description = request.description,
            price = request.price,
            duration = request.duration,
            isActive = true,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
        
        val saved = serviceRepository.save(service)
        logger.info { "Service created: ${saved.id}" }
        
        return saved.toDTO()
    }
    
    fun updateService(id: Long, request: CreateServiceRequest): ServiceDTO {
        val service = serviceRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Service not found") }
        
        val updatedService = service.copy(
            name = request.name,
            description = request.description,
            price = request.price,
            duration = request.duration,
            updatedAt = LocalDateTime.now()
        )
        
        val saved = serviceRepository.save(updatedService)
        logger.info { "Service updated: $id" }
        
        return saved.toDTO()
    }
    
    fun deleteService(id: Long) {
        if (!serviceRepository.existsById(id)) {
            throw IllegalArgumentException("Service not found")
        }
        serviceRepository.deleteById(id)
        logger.info { "Service deleted: $id" }
    }
    
    fun toggleServiceStatus(id: Long): ServiceDTO {
        val service = serviceRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Service not found") }
        
        val updatedService = service.copy(
            isActive = !service.isActive,
            updatedAt = LocalDateTime.now()
        )
        
        val saved = serviceRepository.save(updatedService)
        logger.info { "Service $id status toggled to ${saved.isActive}" }
        
        return saved.toDTO()
    }
    
    private fun Service.toDTO() = ServiceDTO(
        id = id,
        name = name,
        description = description,
        price = price,
        duration = duration,
        isActive = isActive
    )
}
