package com.nursing.admin.service

import com.nursing.admin.dto.AnalyticsDTO
import com.nursing.admin.dto.NurseAnalyticsDTO
import com.nursing.admin.repository.*
import mu.KotlinLogging
import org.springframework.stereotype.Service
import java.math.BigDecimal

private val logger = KotlinLogging.logger {}

@Service
class AnalyticsService(
    private val patientRepository: PatientRepository,
    private val nurseRepository: NurseRepository,
    private val orderRepository: OrderRepository,
    private val paymentRepository: PaymentRepository,
    private val reviewRepository: ReviewRepository
) {
    
    fun getDashboardAnalytics(): AnalyticsDTO {
        val totalPatients = patientRepository.count()
        val totalNurses = nurseRepository.count()
        val totalOrders = orderRepository.count()
        val totalRevenue = paymentRepository.getTotalRevenue() ?: 0.0
        val pendingOrders = orderRepository.countByStatus("PENDING")
        val completedOrders = orderRepository.countByStatus("COMPLETED")
        
        // Calculate average rating
        val averageRating = calculateAverageRating()
        
        // Get top nurses
        val topNurses = getTopNurses()
        
        logger.info { "Dashboard analytics retrieved" }
        
        return AnalyticsDTO(
            totalPatients = totalPatients,
            totalNurses = totalNurses,
            totalOrders = totalOrders,
            totalRevenue = totalRevenue,
            pendingOrders = pendingOrders,
            completedOrders = completedOrders,
            averageRating = averageRating,
            topNurses = topNurses
        )
    }
    
    fun getTopNurses(limit: Int = 5): List<NurseAnalyticsDTO> {
        return nurseRepository.findAll()
            .filter { it.status == "APPROVED" }
            .sortedByDescending { it.rating }
            .take(limit)
            .map { nurse ->
                NurseAnalyticsDTO(
                    nurseId = nurse.id,
                    nurseName = nurse.name,
                    rating = nurse.rating,
                    totalOrders = nurse.totalOrders,
                    completedOrders = nurse.totalOrders // You might want to calculate this separately
                )
            }
    }
    
    fun calculateAverageRating(): Double {
        val reviews = reviewRepository.findAll()
        return if (reviews.isEmpty()) {
            0.0
        } else {
            reviews.map { it.rating }.average()
        }
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
    
    fun getRevenueStats(): Map<String, Any> {
        val totalRevenue = paymentRepository.getTotalRevenue() ?: 0.0
        val completedPayments = paymentRepository.countByStatus("COMPLETED")
        val pendingPayments = paymentRepository.countByStatus("PENDING")
        
        return mapOf(
            "totalRevenue" to totalRevenue,
            "completedPayments" to completedPayments,
            "pendingPayments" to pendingPayments,
            "averageOrderValue" to if (completedPayments > 0) totalRevenue / completedPayments else 0.0
        )
    }
    
    fun getUserStats(): Map<String, Long> {
        return mapOf(
            "totalPatients" to patientRepository.count(),
            "activePatients" to patientRepository.countByStatus("ACTIVE"),
            "inactivePatients" to patientRepository.countByStatus("INACTIVE"),
            "totalNurses" to nurseRepository.count(),
            "approvedNurses" to nurseRepository.countByStatus("APPROVED"),
            "pendingNurses" to nurseRepository.countByStatus("PENDING")
        )
    }
    
    fun getSystemStats(): Map<String, Any> {
        return mapOf(
            "users" to getUserStats(),
            "orders" to getOrderStats(),
            "revenue" to getRevenueStats(),
            "averageRating" to calculateAverageRating()
        )
    }
}
