package com.nursing.admin.entity

import jakarta.persistence.*
import java.time.LocalDateTime
import java.math.BigDecimal

// Admin User Entity
@Entity
@Table(name = "admins")
data class Admin(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    
    @Column(unique = true, nullable = false)
    val email: String = "",
    
    @Column(nullable = false)
    val password: String = "",
    
    @Column(nullable = false)
    val name: String = "",
    
    @Column(nullable = false)
    val role: String = "ADMIN", // ADMIN, SUPER_ADMIN
    
    @Column(nullable = false)
    val isActive: Boolean = true,
    
    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
    
    @Column(nullable = false)
    val updatedAt: LocalDateTime = LocalDateTime.now()
)

// Patient Entity
@Entity
@Table(name = "patients")
data class Patient(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    
    @Column(nullable = false)
    val name: String = "",
    
    @Column(unique = true, nullable = false)
    val email: String = "",
    
    @Column(nullable = false)
    val phone: String = "",
    
    @Column(nullable = false)
    val address: String = "",
    
    @Column(nullable = false)
    val age: Int = 0,
    
    @Column(length = 1000)
    val medicalHistory: String? = null,
    
    @Column(nullable = false)
    val status: String = "ACTIVE", // ACTIVE, INACTIVE, SUSPENDED
    
    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
    
    @Column(nullable = false)
    val updatedAt: LocalDateTime = LocalDateTime.now()
)

// Nurse Entity
@Entity
@Table(name = "nurses")
data class Nurse(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    
    @Column(nullable = false)
    val name: String = "",
    
    @Column(unique = true, nullable = false)
    val email: String = "",
    
    @Column(nullable = false)
    val phone: String = "",
    
    @Column(nullable = false)
    val specialization: String = "", // General, ICU, Pediatric, etc.
    
    @Column(nullable = false)
    val yearsOfExperience: Int = 0,
    
    @Column(nullable = false)
    val licenseNumber: String = "",
    
    @Column(nullable = false)
    val licenseExpiry: LocalDateTime = LocalDateTime.now(),
    
    @Column(nullable = false)
    val status: String = "PENDING", // PENDING, APPROVED, REJECTED, SUSPENDED
    
    @Column(nullable = false)
    val rating: BigDecimal = BigDecimal.ZERO,
    
    @Column(nullable = false)
    val totalOrders: Int = 0,
    
    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
    
    @Column(nullable = false)
    val updatedAt: LocalDateTime = LocalDateTime.now()
)

// Service Entity
@Entity
@Table(name = "services")
data class Service(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    
    @Column(nullable = false)
    val name: String = "",
    
    @Column(length = 1000)
    val description: String? = null,
    
    @Column(nullable = false)
    val price: BigDecimal = BigDecimal.ZERO,
    
    @Column(nullable = false)
    val duration: Int = 0, // in minutes
    
    @Column(nullable = false)
    val isActive: Boolean = true,
    
    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
)

// Order Entity
@Entity
@Table(name = "orders")
data class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    val patient: Patient? = null,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nurse_id")
    val nurse: Nurse? = null,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false)
    val service: Service? = null,
    
    @Column(nullable = false)
    val status: String = "PENDING", // PENDING, ASSIGNED, IN_PROGRESS, COMPLETED, CANCELLED
    
    @Column(nullable = false)
    val scheduledDate: LocalDateTime = LocalDateTime.now(),
    
    @Column
    val completedDate: LocalDateTime? = null,
    
    @Column(nullable = false)
    val totalPrice: BigDecimal = BigDecimal.ZERO,
    
    @Column(length = 1000)
    val notes: String? = null,
    
    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
    
    @Column(nullable = false)
    val updatedAt: LocalDateTime = LocalDateTime.now()
)

// Payment Entity
@Entity
@Table(name = "payments")
data class Payment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    val order: Order? = null,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    val patient: Patient? = null,
    
    @Column(nullable = false)
    val amount: BigDecimal = BigDecimal.ZERO,
    
    @Column(nullable = false)
    val paymentMethod: String = "CARD", // CARD, BANK_TRANSFER, CASH
    
    @Column(nullable = false)
    val status: String = "PENDING", // PENDING, COMPLETED, FAILED, REFUNDED
    
    @Column
    val transactionId: String? = null,
    
    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
    
    @Column(nullable = false)
    val updatedAt: LocalDateTime = LocalDateTime.now()
)

// Commission Entity
@Entity
@Table(name = "commissions")
data class Commission(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nurse_id", nullable = false)
    val nurse: Nurse? = null,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    val order: Order? = null,
    
    @Column(nullable = false)
    val amount: BigDecimal = BigDecimal.ZERO,
    
    @Column(nullable = false)
    val percentage: BigDecimal = BigDecimal.ZERO,
    
    @Column(nullable = false)
    val status: String = "PENDING", // PENDING, APPROVED, PAID
    
    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
    
    @Column(nullable = false)
    val updatedAt: LocalDateTime = LocalDateTime.now()
)

// Review Entity
@Entity
@Table(name = "reviews")
data class Review(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    val order: Order? = null,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nurse_id", nullable = false)
    val nurse: Nurse? = null,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    val patient: Patient? = null,
    
    @Column(nullable = false)
    val rating: Int = 0, // 1-5
    
    @Column(length = 1000)
    val comment: String? = null,
    
    @Column(nullable = false)
    val status: String = "PENDING", // PENDING, APPROVED, REJECTED
    
    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
)

// Complaint Entity
@Entity
@Table(name = "complaints")
data class Complaint(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    val order: Order? = null,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nurse_id")
    val nurse: Nurse? = null,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    val patient: Patient? = null,
    
    @Column(nullable = false)
    val title: String = "",
    
    @Column(length = 2000, nullable = false)
    val description: String = "",
    
    @Column(nullable = false)
    val priority: String = "MEDIUM", // LOW, MEDIUM, HIGH, URGENT
    
    @Column(nullable = false)
    val status: String = "OPEN", // OPEN, IN_PROGRESS, RESOLVED, CLOSED
    
    @Column(length = 1000)
    val resolution: String? = null,
    
    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
    
    @Column(nullable = false)
    val updatedAt: LocalDateTime = LocalDateTime.now()
)

// Notification Entity
@Entity
@Table(name = "notifications")
data class Notification(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    
    @Column(nullable = false)
    val recipientType: String = "ALL", // ALL, PATIENT, NURSE, ADMIN
    
    @Column
    val recipientId: Long? = null,
    
    @Column(nullable = false)
    val title: String = "",
    
    @Column(length = 1000, nullable = false)
    val message: String = "",
    
    @Column(nullable = false)
    val type: String = "GENERAL", // GENERAL, ORDER, PAYMENT, REVIEW, COMPLAINT
    
    @Column(nullable = false)
    val isRead: Boolean = false,
    
    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
)
