package com.example.days.domain.messages.service

import com.example.days.domain.messages.dto.request.CreateMessageRequest
import com.example.days.domain.messages.dto.response.AdminMessagesSendResponse
import com.example.days.domain.messages.dto.response.MessageSendResponse
import com.example.days.domain.messages.dto.response.MessagesReceiveResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface MessagesService {
    fun createMessages(req: CreateMessageRequest, userId: Long): MessageSendResponse

    fun sendMessages(id: Long, userId: Long): MessageSendResponse

    fun sendMessagesAll(pageable: Pageable, userId: Long): Page<MessageSendResponse>

    fun receiverMessages(receiverId: Long, userId: Long): MessagesReceiveResponse

    fun receiverMessagesAll(pageable: Pageable, userId: Long): Page<MessagesReceiveResponse>

    fun deleteSenderMessages(senderId: Long, userId: Long)

    fun deleteReceiverMessages(receiverId: Long, userId: Long)

    fun toUserCreateMessage(req: CreateMessageRequest, userId: Long) : AdminMessagesSendResponse

    fun readMessagesByAdmin(id: Long, userId: Long): AdminMessagesSendResponse

    fun readAllMessagesByAdmin(pageable: Pageable, userId: Long): Page<AdminMessagesSendResponse>

    fun readAllMessagesOnlyAdmin(pageable: Pageable, userId: Long): Page<AdminMessagesSendResponse>

    fun deleteUserByAdminMessages(id: Long, userId: Long)


}