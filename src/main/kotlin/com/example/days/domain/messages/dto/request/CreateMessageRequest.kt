package com.example.days.domain.messages.dto.request

data class CreateMessageRequest(
    val title: String,
    val content: String,
    val receiverNickname: String
)