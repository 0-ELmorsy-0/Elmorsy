package com.nursing.admin.controller

import com.nursing.admin.dto.ApiResponse
import com.nursing.admin.dto.ApproveReviewRequest
import com.nursing.admin.dto.ReviewDTO
import com.nursing.admin.service.ReviewService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin("*")
class ReviewController(
    private val reviewService: ReviewService
) {
    
    @GetMapping
    fun getAllReviews(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(required = false) status: String?
    ): ResponseEntity<ApiResponse<Any>> {
        return try {
            val reviews = reviewService.getAllReviews(page, size, status)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Reviews retrieved successfully",
                    data = reviews
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to retrieve reviews",
                    data = null
                )
            )
        }
    }
    
    @GetMapping("/{id}")
    fun getReviewById(@PathVariable id: Long): ResponseEntity<ApiResponse<ReviewDTO>> {
        return try {
            val review = reviewService.getReviewById(id)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Review retrieved successfully",
                    data = review
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Review not found",
                    data = null
                )
            )
        }
    }
    
    @GetMapping("/nurse/{nurseId}")
    fun getNurseReviews(
        @PathVariable nurseId: Long,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<ApiResponse<Any>> {
        return try {
            val reviews = reviewService.getNurseReviews(nurseId, page, size)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Nurse reviews retrieved successfully",
                    data = reviews
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to retrieve reviews",
                    data = null
                )
            )
        }
    }
    
    @PutMapping("/{id}/approve")
    fun approveReview(
        @PathVariable id: Long,
        @RequestBody request: ApproveReviewRequest
    ): ResponseEntity<ApiResponse<ReviewDTO>> {
        return try {
            val review = reviewService.approveReview(id, request.approve)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Review approval updated successfully",
                    data = review
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to update review approval",
                    data = null
                )
            )
        }
    }
    
    @GetMapping("/stats")
    fun getReviewStats(): ResponseEntity<ApiResponse<Map<String, Long>>> {
        return try {
            val stats = reviewService.getReviewStats()
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Review statistics retrieved successfully",
                    data = stats
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to retrieve statistics",
                    data = null
                )
            )
        }
    }
}
