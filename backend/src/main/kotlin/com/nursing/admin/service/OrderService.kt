package com.nursing.admin.service

import com.nursing.admin.dto.*
import com.nursing.admin.entity.Order
import com.nursing.admin.repository.*
import mu.KotlinLogging
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.time.LocalDateTime

private val logger = KotlinLogging.logger {}

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val patientRepository: PatientRepository,
    private val nurseRepository: NurseRepository,
    private val serviceRepository: ServiceRepository
) {
    
    fun getAllOrders(page: Int = 0, size: Int = 10, status: String? = null): PaginatedResponse<OrderDTO> {
        val pageable = PageRequest.of(page, size)
        
        val orderPage = if (status != null) {
            orderRepository.findAllByStatus(status, pageable)
        } else {
            orderRepository.findAll(pageable)
        }
        
        return PaginatedResponse(
            content = orderPage.content.map { it.toDTO() },
            totalElements = orderPage.totalElements,
            totalPages = orderPage.totalPages,
            currentPage = page,
            pageSize = size
        )
    }
    
    fun getOrderById(id: Long): OrderDTO {
        val order = orderRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Order not found") }
        return order.toDTO()
    }
    
    fun createOrder(request: CreateOrderRequest): OrderDTO {
        val patient = patientRepository.findById(request.patientId)
            .orElseThrow { IllegalArgumentException("Patient not found") }
        
        val service = serviceRepository.findById(request.serviceId)
            .orElseThrow { IllegalArgumentException("Service not found") }
        
        val order = Order(
            patient = patient,
            service = service,
            status = "PENDING",
            scheduledDate = request.scheduledDate,
            totalPrice = service.price,
            notes = request.notes,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
        
        val savedOrder = orderRepository.save(order)
        logger.info { "Order created: ${savedOrder.id}" }
        
        return savedOrder.toDTO()
    }
    
    fun assignNurse(orderId: Long, nurseId: Long): OrderDTO {
        val order = orderRepository.findById(orderId)
            .orElseThrow { IllegalArgumentException("Order not found") }
        
        val nurse = nurseRepository.findById(nurseId)
            .orElseThrow { IllegalArgumentException("Nurse not found") }
        
        if (nurse.status != "APPROVED") {
            throw IllegalArgumentException("Nurse is not approved")
        }
        
        val updatedOrder = order.copy(
            nurse = nurse,
            status = "ASSIGNED",
            updatedAt = LocalDateTime.now()
        )
        
        val saved = orderRepository.save(updatedOrder)
        logger.info { "Nurse $nurseId assigned to order $orderId" }
        
        return saved.toDTO()
    }
    
    fun updateOrderStatus(orderId: Long, newStatus: String): OrderDTO {
        val order = orderRepository.findById(orderId)
            .orElseThrow { IllegalArgumentException("Order not found") }
        
        val completedDate = if (newStatus == "COMPLETED") LocalDateTime.now() else null
        
        val updatedOrder = order.copy(
            status = newStatus,
            completedDate = completedDate,
            updatedAt = LocalDateTime.now()
        )
        
        val saved = orderRepository.save(updatedOrder)
        logger.info { "Order $orderId status changed to $newStatus" }
        
        return saved.toDTO()
    }
    
    fun getOrderStats(): Map<String, Long> {
        return mapOf(
            "total" to orderRepository.count(),
            "pending" to orderRepository.countByStatus("PENDING"),
            "assigned" to orderRepository.countByStatus("ASSIGNED"),
            "in_progress" to orderRepository.countByStatus("IN_PROGRESS"),
            "completed" to orderRepository.countByStatus("COMPLETED"),
            "cancelled" to orderRepository.countByStatus("CANCELLED")
        )
    }
    
    private fun Order.toDTO() = OrderDTO(
        id = id,
        patientId = patient?.id ?: 0,
        patientName = patient?.name ?: "",
        nurseId = nurse?.id,
        nurseName = nurse?.name,
        serviceId = service?.id ?: 0,
        serviceName = service?.name ?: "",
        status = status,
        scheduledDate = scheduledDate,
        completedDate = completedDate,
        totalPrice = totalPrice,
        notes = notes,
        createdAt = createdAt
    )
}
