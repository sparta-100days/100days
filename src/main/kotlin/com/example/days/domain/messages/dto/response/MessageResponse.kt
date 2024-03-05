package com.example.days.domain.messages.dto.response

import java.time.LocalDateTime

data class MessageResponse(
    val id: Long,
    val title: String,
    val content: String,
    val createdAt: LocalDateTime
)
