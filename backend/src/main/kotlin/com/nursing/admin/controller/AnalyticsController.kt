package com.nursing.admin.controller

import com.nursing.admin.dto.ApiResponse
import com.nursing.admin.service.AnalyticsService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/analytics")
@CrossOrigin("*")
class AnalyticsController(
    private val analyticsService: AnalyticsService
) {
    
    @GetMapping("/dashboard")
    fun getDashboardAnalytics(): ResponseEntity<ApiResponse<Any>> {
        return try {
            val analytics = analyticsService.getDashboardAnalytics()
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Dashboard analytics retrieved successfully",
                    data = analytics
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to retrieve analytics",
                    data = null
                )
            )
        }
    }
    
    @GetMapping("/top-nurses")
    fun getTopNurses(
        @RequestParam(defaultValue = "5") limit: Int
    ): ResponseEntity<ApiResponse<Any>> {
        return try {
            val topNurses = analyticsService.getTopNurses(limit)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Top nurses retrieved successfully",
                    data = topNurses
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to retrieve top nurses",
                    data = null
                )
            )
        }
    }
    
    @GetMapping("/average-rating")
    fun getAverageRating(): ResponseEntity<ApiResponse<Map<String, Any>>> {
        return try {
            val rating = analyticsService.calculateAverageRating()
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Average rating retrieved successfully",
                    data = mapOf("averageRating" to rating)
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to retrieve average rating",
                    data = null
                )
            )
        }
    }
    
    @GetMapping("/orders/stats")
    fun getOrderStats(): ResponseEntity<ApiResponse<Map<String, Long>>> {
        return try {
            val stats = analyticsService.getOrderStats()
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Order statistics retrieved successfully",
                    data = stats
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to retrieve order statistics",
                    data = null
                )
            )
        }
    }
    
    @GetMapping("/revenue/stats")
    fun getRevenueStats(): ResponseEntity<ApiResponse<Map<String, Any>>> {
        return try {
            val stats = analyticsService.getRevenueStats()
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Revenue statistics retrieved successfully",
                    data = stats
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to retrieve revenue statistics",
                    data = null
                )
            )
        }
    }
    
    @GetMapping("/users/stats")
    fun getUserStats(): ResponseEntity<ApiResponse<Map<String, Long>>> {
        return try {
            val stats = analyticsService.getUserStats()
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "User statistics retrieved successfully",
                    data = stats
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to retrieve user statistics",
                    data = null
                )
            )
        }
    }
    
    @GetMapping("/system/stats")
    fun getSystemStats(): ResponseEntity<ApiResponse<Map<String, Any>>> {
        return try {
            val stats = analyticsService.getSystemStats()
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "System statistics retrieved successfully",
                    data = stats
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to retrieve system statistics",
                    data = null
                )
            )
        }
    }
}
