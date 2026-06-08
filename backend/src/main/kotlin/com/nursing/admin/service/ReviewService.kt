package com.nursing.admin.service

import com.nursing.admin.dto.*
import com.nursing.admin.entity.Review
import com.nursing.admin.repository.*
import mu.KotlinLogging
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.time.LocalDateTime

private val logger = KotlinLogging.logger {}

@Service
class ReviewService(
    private val reviewRepository: ReviewRepository,
    private val orderRepository: OrderRepository,
    private val nurseRepository: NurseRepository,
    private val patientRepository: PatientRepository
) {
    
    fun getAllReviews(page: Int = 0, size: Int = 10, status: String? = null): PaginatedResponse<ReviewDTO> {
        val pageable = PageRequest.of(page, size)
        
        val reviewPage = if (status != null) {
            reviewRepository.findAllByStatus(status, pageable)
        } else {
            reviewRepository.findAll(pageable)
        }
        
        return PaginatedResponse(
            content = reviewPage.content.map { it.toDTO() },
            totalElements = reviewPage.totalElements,
            totalPages = reviewPage.totalPages,
            currentPage = page,
            pageSize = size
        )
    }
    
    fun getReviewById(id: Long): ReviewDTO {
        val review = reviewRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Review not found") }
        return review.toDTO()
    }
    
    fun getNurseReviews(nurseId: Long, page: Int = 0, size: Int = 10): PaginatedResponse<ReviewDTO> {
        val pageable = PageRequest.of(page, size)
        val reviewPage = reviewRepository.findAllByNurseId(nurseId, pageable)
        
        return PaginatedResponse(
            content = reviewPage.content.map { it.toDTO() },
            totalElements = reviewPage.totalElements,
            totalPages = reviewPage.totalPages,
            currentPage = page,
            pageSize = size
        )
    }
    
    fun approveReview(reviewId: Long, approve: Boolean): ReviewDTO {
        val review = reviewRepository.findById(reviewId)
            .orElseThrow { IllegalArgumentException("Review not found") }
        
        val status = if (approve) "APPROVED" else "REJECTED"
        val updatedReview = review.copy(
            status = status
        )
        
        val saved = reviewRepository.save(updatedReview)
        logger.info { "Review $reviewId status changed to $status" }
        
        return saved.toDTO()
    }
    
    fun getReviewStats(): Map<String, Long> {
        return mapOf(
            "total" to reviewRepository.count(),
            "pending" to reviewRepository.countByStatus("PENDING"),
            "approved" to reviewRepository.countByStatus("APPROVED"),
            "rejected" to reviewRepository.countByStatus("REJECTED")
        )
    }
    
    private fun Review.toDTO() = ReviewDTO(
        id = id,
        orderId = order?.id ?: 0,
        nurseId = nurse?.id ?: 0,
        nurseName = nurse?.name ?: "",
        patientId = patient?.id ?: 0,
        patientName = patient?.name ?: "",
        rating = rating,
        comment = comment,
        status = status,
        createdAt = createdAt
    )
}
