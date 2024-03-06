package com.example.days.domain.messages.service

import com.example.days.domain.messages.dto.request.CreateMessageRequest
import com.example.days.domain.messages.dto.response.MessageResponse
import com.example.days.domain.user.model.User
import com.example.days.global.infra.security.UserPrincipal

interface MessagesService {
    fun createMessages(req: CreateMessageRequest, user: User): MessageResponse

    fun sendMessages(id: Long, user: User): MessageResponse

    fun sendMessagesAll(user: User): List<MessageResponse>

    fun receiverMessages(id: Long, user: User): MessageResponse

    fun receiverMessagesAll(user: User): List<MessageResponse>

    fun deleteSenderMessages(id: Long, user: User)

    fun deleteReceiverMessages(id: Long, user: User)
}