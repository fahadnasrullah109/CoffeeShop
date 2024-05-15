package com.coffee.shop.data.mappers

import com.coffee.shop.data.models.response.Notification
import com.coffee.shop.domain.models.DomainNotification

class NotificationMapper {
    fun mapToDomain(notification: Notification): DomainNotification {
        return DomainNotification(
            type = notification.type,
            title = notification.title,
            message = notification.message,
            date = notification.date
        )
    }
}