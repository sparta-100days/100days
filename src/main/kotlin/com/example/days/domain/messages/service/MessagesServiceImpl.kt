package com.example.days.domain.messages.service

import com.example.days.domain.messages.dto.request.CreateMessageRequest
import com.example.days.domain.messages.dto.response.MessageSendResponse
import com.example.days.domain.messages.dto.response.MessagesReceiveResponse
import com.example.days.domain.messages.model.MessagesEntity
import com.example.days.domain.messages.repository.MessagesRepository
import com.example.days.domain.user.repository.UserRepository
import com.example.days.global.common.exception.ModelNotFoundException
import com.example.days.global.common.exception.NoReceiverMessagesException
import com.example.days.global.common.exception.NoSendMessagesException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MessagesServiceImpl(
    private val messagesRepository: MessagesRepository,
    private val userRepository: UserRepository
) : MessagesService {
    override fun createMessages(req: CreateMessageRequest, userId: Long): MessageSendResponse {
        val receiverNickname = userRepository.findByNickname(req.receiverNickname) ?: TODO()
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User", userId)

        val messages = messagesRepository.save(
            MessagesEntity(
                title = req.title,
                content = req.content,
                receiver = receiverNickname,
                sender = user,
                deletedBySender = false,
                deletedByReceiver = false
            )
        )
        return MessageSendResponse.from(messages)
    }

    @Transactional
    override fun sendMessages(id: Long, userId: Long): MessageSendResponse {
        val sender = messagesRepository.findByIdOrNull(id) ?: throw ModelNotFoundException("Messages", id)
        if (sender.deletedBySender){
            throw ModelNotFoundException("Messages", id)
        }
        if(sender.sender.id != userId){
            throw NoSendMessagesException(id)
        }
        sender.readStatus()
        messagesRepository.save(sender)
        return MessageSendResponse.from(sender)
    }

    // 수정할 필요 있음
    @Transactional(readOnly = true)
    override fun sendMessagesAll(userId: Long): List<MessageSendResponse> {
        return messagesRepository.findAllBySenderIdAndDeletedBySenderFalseOrderByIdDesc(userId).map { MessageSendResponse.from(it) }
    }

    @Transactional
    override fun receiverMessages(id: Long, userId: Long): MessagesReceiveResponse {
        val receiver = messagesRepository.findByIdOrNull(id) ?: throw ModelNotFoundException("Messages", id)
        if (receiver.deletedByReceiver){
            throw ModelNotFoundException("Messages", id)
        }
        if(receiver.receiver.id != userId){
            throw NoReceiverMessagesException(id)
        }
        receiver.readStatus()
        messagesRepository.save(receiver)
        return MessagesReceiveResponse.from(receiver)
    }

    // 수정할 필요 있음
    @Transactional(readOnly = true)
    override fun receiverMessagesAll(userId: Long): List<MessagesReceiveResponse> {
        return messagesRepository.findAllByReceiverIdAndDeletedByReceiverFalseOrderByIdDesc(userId).map { MessagesReceiveResponse.from(it) }
    }

    @Transactional
    override fun deleteSenderMessages(id: Long, userId: Long) {
        val messages = messagesRepository.findByIdOrNull(id) ?: throw ModelNotFoundException("Messages", id)
        if(messages.sender.id != userId){
            throw NoSendMessagesException(id)
        }
        messages.deletedBySender()
        messagesRepository.save(messages)
    }

    @Transactional
    override fun deleteReceiverMessages(id: Long, userId: Long) {
        val messages = messagesRepository.findByIdOrNull(id) ?: throw ModelNotFoundException("Messages", id)
        if(messages.receiver.id != userId){
            throw NoReceiverMessagesException(id)
        }
        messages.deletedByReceiver()
        messagesRepository.save(messages)
    }
}