package com.nursing.admin.service

import com.nursing.admin.dto.*
import com.nursing.admin.entity.Notification
import com.nursing.admin.repository.NotificationRepository
import mu.KotlinLogging
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.time.LocalDateTime

private val logger = KotlinLogging.logger {}

@Service
class NotificationService(
    private val notificationRepository: NotificationRepository
) {
    
    fun getAllNotifications(page: Int = 0, size: Int = 10): PaginatedResponse<NotificationDTO> {
        val pageable = PageRequest.of(page, size)
        val notificationPage = notificationRepository.findAll(pageable)
        
        return PaginatedResponse(
            content = notificationPage.content.map { it.toDTO() },
            totalElements = notificationPage.totalElements,
            totalPages = notificationPage.totalPages,
            currentPage = page,
            pageSize = size
        )
    }
    
    fun getNotificationById(id: Long): NotificationDTO {
        val notification = notificationRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Notification not found") }
        return notification.toDTO()
    }
    
    fun getUserNotifications(userId: Long, page: Int = 0, size: Int = 10): PaginatedResponse<NotificationDTO> {
        val pageable = PageRequest.of(page, size)
        val notificationPage = notificationRepository.findAllByRecipientIdOrderByCreatedAtDesc(userId, pageable)
        
        return PaginatedResponse(
            content = notificationPage.content.map { it.toDTO() },
            totalElements = notificationPage.totalElements,
            totalPages = notificationPage.totalPages,
            currentPage = page,
            pageSize = size
        )
    }
    
    fun sendNotification(request: SendNotificationRequest): NotificationDTO {
        val notification = Notification(
            recipientType = request.recipientType,
            recipientId = request.recipientId,
            title = request.title,
            message = request.message,
            type = request.type,
            isRead = false,
            createdAt = LocalDateTime.now()
        )
        
        val saved = notificationRepository.save(notification)
        logger.info { "Notification sent: ${saved.id}" }
        
        return saved.toDTO()
    }
    
    fun markAsRead(notificationId: Long): NotificationDTO {
        val notification = notificationRepository.findById(notificationId)
            .orElseThrow { IllegalArgumentException("Notification not found") }
        
        val updatedNotification = notification.copy(
            isRead = true
        )
        
        val saved = notificationRepository.save(updatedNotification)
        logger.info { "Notification $notificationId marked as read" }
        
        return saved.toDTO()
    }
    
    fun deleteNotification(notificationId: Long) {
        if (!notificationRepository.existsById(notificationId)) {
            throw IllegalArgumentException("Notification not found")
        }
        notificationRepository.deleteById(notificationId)
        logger.info { "Notification $notificationId deleted" }
    }
    
    fun getUnreadCount(userId: Long): Long {
        return notificationRepository.countByRecipientIdAndIsRead(userId, false)
    }
    
    private fun Notification.toDTO() = NotificationDTO(
        id = id,
        recipientType = recipientType,
        recipientId = recipientId,
        title = title,
        message = message,
        type = type,
        isRead = isRead,
        createdAt = createdAt
    )
}
