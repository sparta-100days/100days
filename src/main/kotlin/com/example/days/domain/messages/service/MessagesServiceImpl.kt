package com.example.days.domain.messages.service

import com.example.days.domain.messages.dto.request.CreateMessageRequest
import com.example.days.domain.messages.dto.response.MessageResponse
import com.example.days.domain.messages.repository.MessagesRepository
import com.example.days.domain.user.model.User
import org.springframework.stereotype.Service

@Service
class MessagesServiceImpl(
    private val messagesRepository: MessagesRepository
): MessagesService {
    override fun createMessages(req: CreateMessageRequest): MessageResponse {
        TODO("Not yet implemented")
    }

    override fun sendMessages(senderId: Long):MessageResponse {
        TODO("Not yet implemented")
    }

    override fun sendMessagesAll(): List<MessageResponse> {
        TODO("Not yet implemented")
    }

    override fun receiverMessages(receiverId: Long):MessageResponse {
        TODO("Not yet implemented")
    }

    override fun receiverMessagesAll(): List<MessageResponse> {
        TODO("Not yet implemented")
    }

    override fun deleteSenderMessages(senderId: Long) {
        TODO("Not yet implemented")
    }

    override fun deleteReceiverMessages(receiverId: Long) {
        TODO("Not yet implemented")
    }
}