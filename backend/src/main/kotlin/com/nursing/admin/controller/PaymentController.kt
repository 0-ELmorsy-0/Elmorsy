package com.nursing.admin.controller

import com.nursing.admin.dto.ApiResponse
import com.nursing.admin.dto.CreatePaymentRequest
import com.nursing.admin.dto.PaymentDTO
import com.nursing.admin.service.PaymentService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RestController
@RequestMapping("/api/payments")
@CrossOrigin("*")
class PaymentController(
    private val paymentService: PaymentService
) {
    
    @GetMapping
    fun getAllPayments(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(required = false) status: String?
    ): ResponseEntity<ApiResponse<Any>> {
        return try {
            val payments = paymentService.getAllPayments(page, size, status)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Payments retrieved successfully",
                    data = payments
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to retrieve payments",
                    data = null
                )
            )
        }
    }
    
    @GetMapping("/{id}")
    fun getPaymentById(@PathVariable id: Long): ResponseEntity<ApiResponse<PaymentDTO>> {
        return try {
            val payment = paymentService.getPaymentById(id)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Payment retrieved successfully",
                    data = payment
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Payment not found",
                    data = null
                )
            )
        }
    }
    
    @PostMapping
    fun createPayment(@RequestBody request: CreatePaymentRequest): ResponseEntity<ApiResponse<PaymentDTO>> {
        return try {
            val payment = paymentService.createPayment(request)
            ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse(
                    success = true,
                    message = "Payment created successfully",
                    data = payment
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to create payment",
                    data = null
                )
            )
        }
    }
    
    @PutMapping("/{id}/complete")
    fun completePayment(
        @PathVariable id: Long,
        @RequestParam transactionId: String
    ): ResponseEntity<ApiResponse<PaymentDTO>> {
        return try {
            val payment = paymentService.completePayment(id, transactionId)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Payment completed successfully",
                    data = payment
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to complete payment",
                    data = null
                )
            )
        }
    }
    
    @GetMapping("/revenue")
    fun getTotalRevenue(): ResponseEntity<ApiResponse<Map<String, Any>>> {
        return try {
            val revenue = paymentService.getTotalRevenue()
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Revenue retrieved successfully",
                    data = mapOf("totalRevenue" to revenue)
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to retrieve revenue",
                    data = null
                )
            )
        }
    }
    
    @GetMapping("/revenue/range")
    fun getRevenueByDateRange(
        @RequestParam startDate: String,
        @RequestParam endDate: String
    ): ResponseEntity<ApiResponse<Map<String, Any>>> {
        return try {
            val formatter = DateTimeFormatter.ISO_DATE_TIME
            val start = LocalDateTime.parse(startDate, formatter)
            val end = LocalDateTime.parse(endDate, formatter)
            
            val revenue = paymentService.getRevenueByDateRange(start, end)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Revenue retrieved successfully",
                    data = mapOf(
                        "revenue" to revenue,
                        "startDate" to startDate,
                        "endDate" to endDate
                    )
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to retrieve revenue",
                    data = null
                )
            )
        }
    }
    
    @GetMapping("/stats")
    fun getPaymentStats(): ResponseEntity<ApiResponse<Map<String, Any>>> {
        return try {
            val stats = paymentService.getPaymentStats()
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Payment statistics retrieved successfully",
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
