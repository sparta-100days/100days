package com.example.days.domain.messages.dto.response

import com.example.days.domain.messages.model.AdminMessagesEntity
import java.time.LocalDateTime

data class AdminMessagesSendResponse(
    val id: Long,
    val title: String,
    val content: String,
    val receiverNickname: String,
    val createdAt: LocalDateTime,
    val readStatus: Boolean
) {
    companion object {
        fun from(messages: AdminMessagesEntity): AdminMessagesSendResponse {
            return AdminMessagesSendResponse(
                id = messages.id!!,
                title = messages.title,
                content = messages.content,
                receiverNickname = messages.receiver.nickname,
                createdAt = messages.sentAt,
                readStatus = messages.readStatus
            )
        }
    }
}
