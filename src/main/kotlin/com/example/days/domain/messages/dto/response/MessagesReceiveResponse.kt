package com.example.days.domain.messages.dto.response

import com.example.days.domain.messages.model.MessagesEntity
import java.time.LocalDateTime

data class MessagesReceiveResponse(
    val id: Long,
    val title: String,
    val content: String,
    val senderNickname: String,
    val createdAt: LocalDateTime,
    val readStatus: Boolean
){
    companion object {
        fun from(messages: MessagesEntity): MessagesReceiveResponse {
            return MessagesReceiveResponse(
                id = messages.id!!,
                title = messages.title,
                content = messages.content,
                senderNickname = messages.sender.nickname,
                createdAt = messages.createdAt,
                readStatus = messages.readStatus
            )
        }
    }
}
