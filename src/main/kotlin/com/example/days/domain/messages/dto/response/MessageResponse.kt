package com.example.days.domain.messages.dto.response

import com.example.days.domain.messages.model.MessagesEntity
import java.time.LocalDateTime

data class MessageResponse(
    val id: Long,
    val title: String,
    val content: String,
    val senderNickname: String,
    val receiverNickname: String,
    val createdAt: LocalDateTime
) {
    companion object {
        fun from(messages: MessagesEntity): MessageResponse {
            return MessageResponse(
                id = messages.id!!,
                title = messages.title,
                content = messages.content,
                senderNickname = messages.sender.toString(),
                receiverNickname = messages.receiver.toString(),
                createdAt = messages.createdAt
            )
        }
    }
}
