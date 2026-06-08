package com.nursing.admin.dto

import java.math.BigDecimal
import java.time.LocalDateTime

// Auth DTOs
data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val token: String,
    val admin: AdminDTO
)

data class AdminDTO(
    val id: Long,
    val email: String,
    val name: String,
    val role: String,
    val isActive: Boolean,
    val createdAt: LocalDateTime
)

// Patient DTOs
data class PatientDTO(
    val id: Long,
    val name: String,
    val email: String,
    val phone: String,
    val address: String,
    val age: Int,
    val medicalHistory: String?,
    val status: String,
    val createdAt: LocalDateTime
)

data class CreatePatientRequest(
    val name: String,
    val email: String,
    val phone: String,
    val address: String,
    val age: Int,
    val medicalHistory: String?
)

data class UpdatePatientRequest(
    val name: String?,
    val phone: String?,
    val address: String?,
    val age: Int?,
    val medicalHistory: String?,
    val status: String?
)

// Nurse DTOs
data class NurseDTO(
    val id: Long,
    val name: String,
    val email: String,
    val phone: String,
    val specialization: String,
    val yearsOfExperience: Int,
    val licenseNumber: String,
    val licenseExpiry: LocalDateTime,
    val status: String,
    val rating: BigDecimal,
    val totalOrders: Int,
    val createdAt: LocalDateTime
)

data class CreateNurseRequest(
    val name: String,
    val email: String,
    val phone: String,
    val specialization: String,
    val yearsOfExperience: Int,
    val licenseNumber: String,
    val licenseExpiry: LocalDateTime
)

data class ApproveNurseRequest(
    val nurseId: Long,
    val approve: Boolean
)

// Service DTOs
data class ServiceDTO(
    val id: Long,
    val name: String,
    val description: String?,
    val price: BigDecimal,
    val duration: Int,
    val isActive: Boolean
)

data class CreateServiceRequest(
    val name: String,
    val description: String?,
    val price: BigDecimal,
    val duration: Int
)

// Order DTOs
data class OrderDTO(
    val id: Long,
    val patientId: Long,
    val patientName: String,
    val nurseId: Long?,
    val nurseName: String?,
    val serviceId: Long,
    val serviceName: String,
    val status: String,
    val scheduledDate: LocalDateTime,
    val completedDate: LocalDateTime?,
    val totalPrice: BigDecimal,
    val notes: String?,
    val createdAt: LocalDateTime
)

data class CreateOrderRequest(
    val patientId: Long,
    val serviceId: Long,
    val scheduledDate: LocalDateTime,
    val notes: String?
)

data class AssignNurseRequest(
    val orderId: Long,
    val nurseId: Long
)

data class UpdateOrderStatusRequest(
    val orderId: Long,
    val status: String
)

// Payment DTOs
data class PaymentDTO(
    val id: Long,
    val orderId: Long,
    val patientId: Long,
    val patientName: String,
    val amount: BigDecimal,
    val paymentMethod: String,
    val status: String,
    val transactionId: String?,
    val createdAt: LocalDateTime
)

data class CreatePaymentRequest(
    val orderId: Long,
    val patientId: Long,
    val amount: BigDecimal,
    val paymentMethod: String
)

// Commission DTOs
data class CommissionDTO(
    val id: Long,
    val nurseId: Long,
    val nurseName: String,
    val orderId: Long,
    val amount: BigDecimal,
    val percentage: BigDecimal,
    val status: String,
    val createdAt: LocalDateTime
)

data class ApproveCommissionRequest(
    val commissionId: Long,
    val approve: Boolean
)

// Review DTOs
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
    val createdAt: LocalDateTime
)

data class ApproveReviewRequest(
    val reviewId: Long,
    val approve: Boolean
)

// Complaint DTOs
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
    val createdAt: LocalDateTime
)

data class CreateComplaintRequest(
    val orderId: Long,
    val title: String,
    val description: String,
    val priority: String
)

data class ResolveComplaintRequest(
    val complaintId: Long,
    val resolution: String,
    val status: String
)

// Notification DTOs
data class NotificationDTO(
    val id: Long,
    val recipientType: String,
    val recipientId: Long?,
    val title: String,
    val message: String,
    val type: String,
    val isRead: Boolean,
    val createdAt: LocalDateTime
)

data class SendNotificationRequest(
    val recipientType: String,
    val recipientId: Long?,
    val title: String,
    val message: String,
    val type: String
)

// Analytics DTOs
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

data class RevenueChartDTO(
    val date: String,
    val revenue: BigDecimal
)

// Response Wrapper
data class ApiResponse<T>(
    val success: Boolean,
    val message: String,
    val data: T?,
    val timestamp: LocalDateTime = LocalDateTime.now()
)

data class PaginatedResponse<T>(
    val content: List<T>,
    val totalElements: Long,
    val totalPages: Int,
    val currentPage: Int,
    val pageSize: Int
)
