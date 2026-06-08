package com.nursing.admin.service

import com.nursing.admin.dto.*
import com.nursing.admin.entity.Complaint
import com.nursing.admin.repository.*
import mu.KotlinLogging
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.time.LocalDateTime

private val logger = KotlinLogging.logger {}

@Service
class ComplaintService(
    private val complaintRepository: ComplaintRepository,
    private val orderRepository: OrderRepository,
    private val patientRepository: PatientRepository,
    private val nurseRepository: NurseRepository
) {
    
    fun getAllComplaints(page: Int = 0, size: Int = 10, status: String? = null, priority: String? = null): PaginatedResponse<ComplaintDTO> {
        val pageable = PageRequest.of(page, size)
        
        val complaintPage = when {
            status != null -> complaintRepository.findAllByStatus(status, pageable)
            priority != null -> complaintRepository.findAllByPriority(priority, pageable)
            else -> complaintRepository.findAll(pageable)
        }
        
        return PaginatedResponse(
            content = complaintPage.content.map { it.toDTO() },
            totalElements = complaintPage.totalElements,
            totalPages = complaintPage.totalPages,
            currentPage = page,
            pageSize = size
        )
    }
    
    fun getComplaintById(id: Long): ComplaintDTO {
        val complaint = complaintRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Complaint not found") }
        return complaint.toDTO()
    }
    
    fun createComplaint(request: CreateComplaintRequest): ComplaintDTO {
        val order = orderRepository.findById(request.orderId)
            .orElseThrow { IllegalArgumentException("Order not found") }
        
        val patient = order.patient
            ?: throw IllegalArgumentException("Patient not found")
        
        val complaint = Complaint(
            order = order,
            nurse = order.nurse,
            patient = patient,
            title = request.title,
            description = request.description,
            priority = request.priority,
            status = "OPEN",
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
        
        val saved = complaintRepository.save(complaint)
        logger.info { "Complaint created: ${saved.id}" }
        
        return saved.toDTO()
    }
    
    fun resolveComplaint(complaintId: Long, resolution: String, status: String): ComplaintDTO {
        val complaint = complaintRepository.findById(complaintId)
            .orElseThrow { IllegalArgumentException("Complaint not found") }
        
        val updatedComplaint = complaint.copy(
            status = status,
            resolution = resolution,
            updatedAt = LocalDateTime.now()
        )
        
        val saved = complaintRepository.save(updatedComplaint)
        logger.info { "Complaint $complaintId resolved" }
        
        return saved.toDTO()
    }
    
    fun getComplaintStats(): Map<String, Long> {
        return mapOf(
            "total" to complaintRepository.count(),
            "open" to complaintRepository.countByStatus("OPEN"),
            "in_progress" to complaintRepository.countByStatus("IN_PROGRESS"),
            "resolved" to complaintRepository.countByStatus("RESOLVED"),
            "closed" to complaintRepository.countByStatus("CLOSED")
        )
    }
    
    private fun Complaint.toDTO() = ComplaintDTO(
        id = id,
        orderId = order?.id ?: 0,
        nurseId = nurse?.id,
        nurseName = nurse?.name,
        patientId = patient?.id ?: 0,
        patientName = patient?.name ?: "",
        title = title,
        description = description,
        priority = priority,
        status = status,
        resolution = resolution,
        createdAt = createdAt
    )
}
