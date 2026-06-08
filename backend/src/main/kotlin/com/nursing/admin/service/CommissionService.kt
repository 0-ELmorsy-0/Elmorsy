package com.nursing.admin.service

import com.nursing.admin.dto.*
import com.nursing.admin.entity.Commission
import com.nursing.admin.repository.*
import mu.KotlinLogging
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.time.LocalDateTime

private val logger = KotlinLogging.logger {}

@Service
class CommissionService(
    private val commissionRepository: CommissionRepository,
    private val nurseRepository: NurseRepository,
    private val orderRepository: OrderRepository
) {
    
    fun getAllCommissions(page: Int = 0, size: Int = 10, status: String? = null): PaginatedResponse<CommissionDTO> {
        val pageable = PageRequest.of(page, size)
        
        val commissionPage = if (status != null) {
            commissionRepository.findAllByStatus(status, pageable)
        } else {
            commissionRepository.findAll(pageable)
        }
        
        return PaginatedResponse(
            content = commissionPage.content.map { it.toDTO() },
            totalElements = commissionPage.totalElements,
            totalPages = commissionPage.totalPages,
            currentPage = page,
            pageSize = size
        )
    }
    
    fun getCommissionById(id: Long): CommissionDTO {
        val commission = commissionRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Commission not found") }
        return commission.toDTO()
    }
    
    fun getNurseCommissions(nurseId: Long, page: Int = 0, size: Int = 10): PaginatedResponse<CommissionDTO> {
        val pageable = PageRequest.of(page, size)
        val commissionPage = commissionRepository.findAllByNurseId(nurseId, pageable)
        
        return PaginatedResponse(
            content = commissionPage.content.map { it.toDTO() },
            totalElements = commissionPage.totalElements,
            totalPages = commissionPage.totalPages,
            currentPage = page,
            pageSize = size
        )
    }
    
    fun approveCommission(commissionId: Long, approve: Boolean): CommissionDTO {
        val commission = commissionRepository.findById(commissionId)
            .orElseThrow { IllegalArgumentException("Commission not found") }
        
        val status = if (approve) "APPROVED" else "REJECTED"
        val updatedCommission = commission.copy(
            status = status,
            updatedAt = LocalDateTime.now()
        )
        
        val saved = commissionRepository.save(updatedCommission)
        logger.info { "Commission $commissionId status changed to $status" }
        
        return saved.toDTO()
    }
    
    fun getTotalNurseCommission(nurseId: Long): Double {
        return commissionRepository.getTotalPaidCommissionForNurse(nurseId) ?: 0.0
    }
    
    fun getCommissionStats(): Map<String, Any> {
        return mapOf(
            "total" to commissionRepository.count(),
            "pending" to commissionRepository.countByStatus("PENDING"),
            "approved" to commissionRepository.countByStatus("APPROVED"),
            "paid" to commissionRepository.countByStatus("PAID")
        )
    }
    
    private fun Commission.toDTO() = CommissionDTO(
        id = id,
        nurseId = nurse?.id ?: 0,
        nurseName = nurse?.name ?: "",
        orderId = order?.id ?: 0,
        amount = amount,
        percentage = percentage,
        status = status,
        createdAt = createdAt
    )
}
