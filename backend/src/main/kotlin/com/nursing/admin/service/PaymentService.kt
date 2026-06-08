package com.nursing.admin.service

import com.nursing.admin.dto.*
import com.nursing.admin.entity.Payment
import com.nursing.admin.repository.*
import mu.KotlinLogging
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.time.LocalDateTime

private val logger = KotlinLogging.logger {}

@Service
class PaymentService(
    private val paymentRepository: PaymentRepository,
    private val orderRepository: OrderRepository,
    private val patientRepository: PatientRepository
) {
    
    fun getAllPayments(page: Int = 0, size: Int = 10, status: String? = null): PaginatedResponse<PaymentDTO> {
        val pageable = PageRequest.of(page, size)
        
        val paymentPage = if (status != null) {
            paymentRepository.findAllByStatus(status, pageable)
        } else {
            paymentRepository.findAll(pageable)
        }
        
        return PaginatedResponse(
            content = paymentPage.content.map { it.toDTO() },
            totalElements = paymentPage.totalElements,
            totalPages = paymentPage.totalPages,
            currentPage = page,
            pageSize = size
        )
    }
    
    fun getPaymentById(id: Long): PaymentDTO {
        val payment = paymentRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Payment not found") }
        return payment.toDTO()
    }
    
    fun createPayment(request: CreatePaymentRequest): PaymentDTO {
        val order = orderRepository.findById(request.orderId)
            .orElseThrow { IllegalArgumentException("Order not found") }
        
        val patient = patientRepository.findById(request.patientId)
            .orElseThrow { IllegalArgumentException("Patient not found") }
        
        val payment = Payment(
            order = order,
            patient = patient,
            amount = request.amount,
            paymentMethod = request.paymentMethod,
            status = "PENDING",
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
        
        val savedPayment = paymentRepository.save(payment)
        logger.info { "Payment created: ${savedPayment.id}" }
        
        return savedPayment.toDTO()
    }
    
    fun completePayment(paymentId: Long, transactionId: String): PaymentDTO {
        val payment = paymentRepository.findById(paymentId)
            .orElseThrow { IllegalArgumentException("Payment not found") }
        
        val updatedPayment = payment.copy(
            status = "COMPLETED",
            transactionId = transactionId,
            updatedAt = LocalDateTime.now()
        )
        
        val saved = paymentRepository.save(updatedPayment)
        logger.info { "Payment $paymentId completed" }
        
        return saved.toDTO()
    }
    
    fun getTotalRevenue(): Double {
        return paymentRepository.getTotalRevenue() ?: 0.0
    }
    
    fun getRevenueByDateRange(startDate: LocalDateTime, endDate: LocalDateTime): Double {
        return paymentRepository.getRevenueByDateRange(startDate, endDate) ?: 0.0
    }
    
    fun getPaymentStats(): Map<String, Any> {
        return mapOf(
            "total" to paymentRepository.count(),
            "pending" to paymentRepository.countByStatus("PENDING"),
            "completed" to paymentRepository.countByStatus("COMPLETED"),
            "failed" to paymentRepository.countByStatus("FAILED"),
            "refunded" to paymentRepository.countByStatus("REFUNDED"),
            "total_revenue" to getTotalRevenue()
        )
    }
    
    private fun Payment.toDTO() = PaymentDTO(
        id = id,
        orderId = order?.id ?: 0,
        patientId = patient?.id ?: 0,
        patientName = patient?.name ?: "",
        amount = amount,
        paymentMethod = paymentMethod,
        status = status,
        transactionId = transactionId,
        createdAt = createdAt
    )
}
