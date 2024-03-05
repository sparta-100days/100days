package com.example.days.domain.messages.service

import com.example.days.domain.messages.dto.request.CreateMessageRequest
import com.example.days.domain.messages.dto.response.MessageResponse

interface MessagesService {
    fun createMessages(req: CreateMessageRequest): MessageResponse

    fun sendMessages(senderId: Long): MessageResponse

    fun sendMessagesAll(): List<MessageResponse>

    fun receiverMessages(receiverId: Long): MessageResponse

    fun receiverMessagesAll(): List<MessageResponse>

    fun deleteSenderMessages(senderId: Long)

    fun deleteReceiverMessages(receiverId: Long)
}