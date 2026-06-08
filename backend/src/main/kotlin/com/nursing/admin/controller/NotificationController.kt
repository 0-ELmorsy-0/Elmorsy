package com.nursing.admin.controller

import com.nursing.admin.dto.ApiResponse
import com.nursing.admin.dto.NotificationDTO
import com.nursing.admin.dto.SendNotificationRequest
import com.nursing.admin.service.NotificationService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin("*")
class NotificationController(
    private val notificationService: NotificationService
) {
    
    @GetMapping
    fun getAllNotifications(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<ApiResponse<Any>> {
        return try {
            val notifications = notificationService.getAllNotifications(page, size)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Notifications retrieved successfully",
                    data = notifications
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to retrieve notifications",
                    data = null
                )
            )
        }
    }
    
    @GetMapping("/{id}")
    fun getNotificationById(@PathVariable id: Long): ResponseEntity<ApiResponse<NotificationDTO>> {
        return try {
            val notification = notificationService.getNotificationById(id)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Notification retrieved successfully",
                    data = notification
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Notification not found",
                    data = null
                )
            )
        }
    }
    
    @GetMapping("/user/{userId}")
    fun getUserNotifications(
        @PathVariable userId: Long,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<ApiResponse<Any>> {
        return try {
            val notifications = notificationService.getUserNotifications(userId, page, size)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "User notifications retrieved successfully",
                    data = notifications
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to retrieve notifications",
                    data = null
                )
            )
        }
    }
    
    @PostMapping
    fun sendNotification(@RequestBody request: SendNotificationRequest): ResponseEntity<ApiResponse<NotificationDTO>> {
        return try {
            val notification = notificationService.sendNotification(request)
            ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse(
                    success = true,
                    message = "Notification sent successfully",
                    data = notification
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to send notification",
                    data = null
                )
            )
        }
    }
    
    @PutMapping("/{id}/read")
    fun markAsRead(@PathVariable id: Long): ResponseEntity<ApiResponse<NotificationDTO>> {
        return try {
            val notification = notificationService.markAsRead(id)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Notification marked as read",
                    data = notification
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to mark notification as read",
                    data = null
                )
            )
        }
    }
    
    @DeleteMapping("/{id}")
    fun deleteNotification(@PathVariable id: Long): ResponseEntity<ApiResponse<String>> {
        return try {
            notificationService.deleteNotification(id)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Notification deleted successfully",
                    data = "Notification $id deleted"
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to delete notification",
                    data = null
                )
            )
        }
    }
    
    @GetMapping("/user/{userId}/unread-count")
    fun getUnreadCount(@PathVariable userId: Long): ResponseEntity<ApiResponse<Map<String, Any>>> {
        return try {
            val count = notificationService.getUnreadCount(userId)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Unread count retrieved successfully",
                    data = mapOf("userId" to userId, "unreadCount" to count)
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to retrieve unread count",
                    data = null
                )
            )
        }
    }
}
