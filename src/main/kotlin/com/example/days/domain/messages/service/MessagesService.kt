package com.example.days.domain.messages.service

import com.example.days.domain.messages.dto.request.CreateMessageRequest
import com.example.days.domain.messages.dto.response.MessageSendResponse
import com.example.days.domain.messages.dto.response.MessagesReceiveResponse

interface MessagesService {
    fun createMessages(req: CreateMessageRequest, userId: Long): MessageSendResponse

    fun sendMessages(id: Long, userId: Long): MessageSendResponse

    fun sendMessagesAll( userId: Long): List<MessageSendResponse>

    fun receiverMessages(receiverId: Long, userId: Long): MessagesReceiveResponse

    fun receiverMessagesAll(userId: Long): List<MessagesReceiveResponse>

    fun deleteSenderMessages(senderId: Long, userId: Long)

    fun deleteReceiverMessages(receiverId: Long, userId: Long)
}