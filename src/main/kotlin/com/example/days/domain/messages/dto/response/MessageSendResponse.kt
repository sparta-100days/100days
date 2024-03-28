package com.example.days.domain.messages.dto.response

import com.example.days.domain.messages.model.MessagesEntity
import java.time.LocalDateTime

data class MessageSendResponse(
    val id: Long,
    val title: String,
    val content: String,
    val senderNickname: String,
    val receiverNickname: String,
    val sentAt: LocalDateTime,
    val readStatus: Boolean
) {
    companion object {
        fun from(messages: MessagesEntity): MessageSendResponse {
            return MessageSendResponse(
                id = messages.id!!,
                title = messages.title,
                content = messages.content,
                receiverNickname = messages.receiver.nickname,
                senderNickname = messages.sender.nickname,
                sentAt = messages.sentAt,
                readStatus = messages.readStatus
            )
        }
    }
}
