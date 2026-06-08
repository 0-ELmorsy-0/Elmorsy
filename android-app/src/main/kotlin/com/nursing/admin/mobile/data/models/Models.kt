package com.nursing.admin.mobile.data.models

import java.math.BigDecimal

// Patient Models
data class PatientDTO(
    val id: Long,
    val name: String,
    val email: String,
    val phone: String,
    val address: String,
    val age: Int,
    val medicalHistory: String?,
    val status: String,
    val createdAt: String
)

// Nurse Models
data class NurseDTO(
    val id: Long,
    val name: String,
    val email: String,
    val phone: String,
    val specialization: String,
    val yearsOfExperience: Int,
    val licenseNumber: String,
    val licenseExpiry: String,
    val status: String,
    val rating: BigDecimal,
    val totalOrders: Int,
    val createdAt: String
)

// Order Models
data class OrderDTO(
    val id: Long,
    val patientId: Long,
    val patientName: String,
    val nurseId: Long?,
    val nurseName: String?,
    val serviceId: Long,
    val serviceName: String,
    val status: String,
    val scheduledDate: String,
    val completedDate: String?,
    val totalPrice: BigDecimal,
    val notes: String?,
    val createdAt: String
)

// Payment Models
data class PaymentDTO(
    val id: Long,
    val orderId: Long,
    val patientId: Long,
    val patientName: String,
    val amount: BigDecimal,
    val paymentMethod: String,
    val status: String,
    val transactionId: String?,
    val createdAt: String
)

// Service Models
data class ServiceDTO(
    val id: Long,
    val name: String,
    val description: String?,
    val price: BigDecimal,
    val duration: Int,
    val isActive: Boolean
)

// Analytics Models
data class AnalyticsDTO(
    val totalPatients: Long,
    val totalNurses: Long,
    val totalOrders: Long,
    val totalRevenue: Double,
    val pendingOrders: Long,
    val completedOrders: Long,
    val averageRating: Double,
    val topNurses: List<NurseAnalyticsDTO>
)

data class NurseAnalyticsDTO(
    val nurseId: Long,
    val nurseName: String,
    val rating: BigDecimal,
    val totalOrders: Int,
    val completedOrders: Int
)

// Notification Models
data class NotificationDTO(
    val id: Long,
    val recipientType: String,
    val recipientId: Long?,
    val title: String,
    val message: String,
    val type: String,
    val isRead: Boolean,
    val createdAt: String
)

// Complaint Models
data class ComplaintDTO(
    val id: Long,
    val orderId: Long,
    val nurseId: Long?,
    val nurseName: String?,
    val patientId: Long,
    val patientName: String,
    val title: String,
    val description: String,
    val priority: String,
    val status: String,
    val resolution: String?,
    val createdAt: String
)

// Review Models
data class ReviewDTO(
    val id: Long,
    val orderId: Long,
    val nurseId: Long,
    val nurseName: String,
    val patientId: Long,
    val patientName: String,
    val rating: Int,
    val comment: String?,
    val status: String,
    val createdAt: String
)

// Commission Models
data class CommissionDTO(
    val id: Long,
    val nurseId: Long,
    val nurseName: String,
    val orderId: Long,
    val amount: BigDecimal,
    val percentage: BigDecimal,
    val status: String,
    val createdAt: String
)

// Pagination Response
data class PaginatedResponse<T>(
    val content: List<T>,
    val totalElements: Long,
    val totalPages: Int,
    val currentPage: Int,
    val pageSize: Int
)
