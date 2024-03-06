package com.example.days.domain.messages.service

import com.example.days.domain.messages.dto.request.CreateMessageRequest
import com.example.days.domain.messages.dto.response.MessageResponse
import com.example.days.domain.messages.model.MessagesEntity
import com.example.days.domain.messages.repository.MessagesRepository
import com.example.days.domain.user.model.User
import com.example.days.domain.user.repository.UserRepository
import com.example.days.global.infra.security.UserPrincipal
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MessagesServiceImpl(
    private val messagesRepository: MessagesRepository,
    private val userRepository: UserRepository
) : MessagesService {
    override fun createMessages(req: CreateMessageRequest, user: User): MessageResponse {
        val messages = messagesRepository.save(
            MessagesEntity(
                title = req.title,
                content = req.content,
                receiver = user,
                sender = user,
                deletedBySender = false,
                deletedByReceiver = false
            )
        )
        return MessageResponse.from(messages)
    }

    @Transactional(readOnly = true)
    override fun sendMessages(id: Long, user: User): MessageResponse {
        val sender = messagesRepository.findByIdOrNull(id) ?: TODO()
        return MessageResponse.from(sender)
    }

    // 수정할 필요 있음
    @Transactional(readOnly = true)
    override fun sendMessagesAll(user: User): List<MessageResponse> {
        return messagesRepository.findAll().map { MessageResponse.from(it) }
    }

    @Transactional(readOnly = true)
    override fun receiverMessages(id: Long, user: User): MessageResponse {
        val receiver = messagesRepository.findByIdOrNull(id) ?: TODO()
        return MessageResponse.from(receiver)
    }

    // 수정할 필요 있음
    @Transactional(readOnly = true)
    override fun receiverMessagesAll(user: User): List<MessageResponse> {
        return messagesRepository.findAll().map { MessageResponse.from(it) }
    }

    @Transactional
    override fun deleteSenderMessages(id: Long, user: User) {
        val messages = messagesRepository.findByIdOrNull(id) ?: TODO()
        messages.deletedBySender()
        messagesRepository.save(messages)
    }

    @Transactional
    override fun deleteReceiverMessages(id: Long, user: User) {
        val messages = messagesRepository.findByIdOrNull(id) ?: TODO()
        messages.deletedByReceiver()
        messagesRepository.save(messages)
    }
}