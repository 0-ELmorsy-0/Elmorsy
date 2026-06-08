package com.nursing.admin.repository

import com.nursing.admin.entity.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

// Admin Repository
@Repository
interface AdminRepository : JpaRepository<Admin, Long> {
    fun findByEmail(email: String): Admin?
    fun existsByEmail(email: String): Boolean
}

// Patient Repository
@Repository
interface PatientRepository : JpaRepository<Patient, Long> {
    fun findByEmail(email: String): Patient?
    fun findAllByStatus(status: String, pageable: Pageable): Page<Patient>
    fun countByStatus(status: String): Long
}

// Nurse Repository
@Repository
interface NurseRepository : JpaRepository<Nurse, Long> {
    fun findByEmail(email: String): Nurse?
    fun findAllByStatus(status: String, pageable: Pageable): Page<Nurse>
    fun countByStatus(status: String): Long
    
    @Query("SELECT n FROM Nurse n WHERE n.specialization = :specialization AND n.status = 'APPROVED' ORDER BY n.rating DESC")
    fun findBySpecializationAndApproved(@Param("specialization") specialization: String, pageable: Pageable): Page<Nurse>
}

// Service Repository
@Repository
interface ServiceRepository : JpaRepository<Service, Long> {
    fun findAllByIsActive(isActive: Boolean, pageable: Pageable): Page<Service>
}

// Order Repository
@Repository
interface OrderRepository : JpaRepository<Order, Long> {
    fun findAllByStatus(status: String, pageable: Pageable): Page<Order>
    fun findAllByPatientId(patientId: Long, pageable: Pageable): Page<Order>
    fun findAllByNurseId(nurseId: Long, pageable: Pageable): Page<Order>
    fun countByStatus(status: String): Long
    
    @Query("SELECT o FROM Order o WHERE o.scheduledDate BETWEEN :startDate AND :endDate")
    fun findByDateRange(@Param("startDate") startDate: LocalDateTime, @Param("endDate") endDate: LocalDateTime, pageable: Pageable): Page<Order>
}

// Payment Repository
@Repository
interface PaymentRepository : JpaRepository<Payment, Long> {
    fun findAllByStatus(status: String, pageable: Pageable): Page<Payment>
    fun findAllByPatientId(patientId: Long, pageable: Pageable): Page<Payment>
    fun countByStatus(status: String): Long
    
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.status = 'COMPLETED'")
    fun getTotalRevenue(): Double?
    
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.status = 'COMPLETED' AND p.createdAt BETWEEN :startDate AND :endDate")
    fun getRevenueByDateRange(@Param("startDate") startDate: LocalDateTime, @Param("endDate") endDate: LocalDateTime): Double?
}

// Commission Repository
@Repository
interface CommissionRepository : JpaRepository<Commission, Long> {
    fun findAllByStatus(status: String, pageable: Pageable): Page<Commission>
    fun findAllByNurseId(nurseId: Long, pageable: Pageable): Page<Commission>
    fun countByStatus(status: String): Long
    
    @Query("SELECT SUM(c.amount) FROM Commission c WHERE c.nurse.id = :nurseId AND c.status = 'PAID'")
    fun getTotalPaidCommissionForNurse(@Param("nurseId") nurseId: Long): Double?
}

// Review Repository
@Repository
interface ReviewRepository : JpaRepository<Review, Long> {
    fun findAllByStatus(status: String, pageable: Pageable): Page<Review>
    fun findAllByNurseId(nurseId: Long, pageable: Pageable): Page<Review>
    fun countByStatus(status: String): Long
}

// Complaint Repository
@Repository
interface ComplaintRepository : JpaRepository<Complaint, Long> {
    fun findAllByStatus(status: String, pageable: Pageable): Page<Complaint>
    fun findAllByPriority(priority: String, pageable: Pageable): Page<Complaint>
    fun countByStatus(status: String): Long
}

// Notification Repository
@Repository
interface NotificationRepository : JpaRepository<Notification, Long> {
    fun findAllByRecipientIdOrderByCreatedAtDesc(recipientId: Long, pageable: Pageable): Page<Notification>
    fun countByRecipientIdAndIsRead(recipientId: Long, isRead: Boolean): Long
}
