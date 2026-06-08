package com.nursing.admin.controller

import com.nursing.admin.dto.*
import com.nursing.admin.service.OrderService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/orders")
@CrossOrigin("*")
class OrderController(
    private val orderService: OrderService
) {
    
    @GetMapping
    fun getAllOrders(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(required = false) status: String?
    ): ResponseEntity<ApiResponse<Any>> {
        return try {
            val orders = orderService.getAllOrders(page, size, status)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Orders retrieved successfully",
                    data = orders
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to retrieve orders",
                    data = null
                )
            )
        }
    }
    
    @GetMapping("/{id}")
    fun getOrderById(@PathVariable id: Long): ResponseEntity<ApiResponse<OrderDTO>> {
        return try {
            val order = orderService.getOrderById(id)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Order retrieved successfully",
                    data = order
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Order not found",
                    data = null
                )
            )
        }
    }
    
    @PostMapping
    fun createOrder(@RequestBody request: CreateOrderRequest): ResponseEntity<ApiResponse<OrderDTO>> {
        return try {
            val order = orderService.createOrder(request)
            ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse(
                    success = true,
                    message = "Order created successfully",
                    data = order
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to create order",
                    data = null
                )
            )
        }
    }
    
    @PutMapping("/{id}/assign-nurse")
    fun assignNurse(
        @PathVariable id: Long,
        @RequestBody request: AssignNurseRequest
    ): ResponseEntity<ApiResponse<OrderDTO>> {
        return try {
            val order = orderService.assignNurse(id, request.nurseId)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Nurse assigned successfully",
                    data = order
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to assign nurse",
                    data = null
                )
            )
        }
    }
    
    @PutMapping("/{id}/status")
    fun updateOrderStatus(
        @PathVariable id: Long,
        @RequestBody request: UpdateOrderStatusRequest
    ): ResponseEntity<ApiResponse<OrderDTO>> {
        return try {
            val order = orderService.updateOrderStatus(id, request.status)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Order status updated successfully",
                    data = order
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to update order status",
                    data = null
                )
            )
        }
    }
    
    @GetMapping("/stats")
    fun getOrderStats(): ResponseEntity<ApiResponse<Map<String, Long>>> {
        return try {
            val stats = orderService.getOrderStats()
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
                    message = e.message ?: "Failed to retrieve statistics",
                    data = null
                )
            )
        }
    }
}
