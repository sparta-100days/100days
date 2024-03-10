package com.example.days.domain.messages.service

import com.example.days.domain.admin.repository.AdminRepository
import com.example.days.domain.messages.dto.request.CreateMessageRequest
import com.example.days.domain.messages.dto.response.AdminMessagesSendResponse
import com.example.days.domain.messages.dto.response.MessageSendResponse
import com.example.days.domain.messages.dto.response.MessagesReceiveResponse
import com.example.days.domain.messages.model.MessagesEntity
import com.example.days.domain.messages.repository.AdminMessagesRepository
import com.example.days.domain.messages.repository.MessagesRepository
import com.example.days.domain.user.model.Status
import com.example.days.domain.user.repository.UserRepository
import com.example.days.global.common.exception.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MessagesServiceImpl(
    private val messagesRepository: MessagesRepository,
    private val userRepository: UserRepository,
    private val adminRepository: AdminRepository,
    private val adminMessagesRepository: AdminMessagesRepository
) : MessagesService {
    override fun createMessages(req: CreateMessageRequest, userId: Long): MessageSendResponse {
        val receiverNickname = userRepository.findByNickname(req.receiverNickname) ?: TODO()
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User", userId)

        if (receiverNickname.status == Status.BAN || receiverNickname.status == Status.WITHDRAW || user.status == Status.BAN || user.status == Status.WITHDRAW) {
            throw NotMessagesException("이 닉네임은 이미 밴이나 탈퇴처리되어 있어 쪽지를 보낼 수도 받을 수도 없습니다!")
        }

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
        if (sender.deletedBySender) {
            throw ModelNotFoundException("Messages", id)
        }
        if (sender.sender.id != userId) {
            throw NoSendMessagesException(id)
        }
        sender.readStatus()
        messagesRepository.save(sender)
        return MessageSendResponse.from(sender)
    }

    // 수정할 필요 있음
    @Transactional(readOnly = true)
    override fun sendMessagesAll(pageable: Pageable, userId: Long): Page<MessageSendResponse> {
        return messagesRepository.findAllBySenderIdAndDeletedBySenderFalseOrderByIdDesc(pageable, userId)
            .map { MessageSendResponse.from(it) }
    }

    @Transactional
    override fun receiverMessages(id: Long, userId: Long): MessagesReceiveResponse {
        val receiver = messagesRepository.findByIdOrNull(id) ?: throw ModelNotFoundException("Messages", id)
        if (receiver.deletedByReceiver) {
            throw ModelNotFoundException("Messages", id)
        }
        if (receiver.receiver.id != userId) {
            throw NoReceiverMessagesException(id)
        }
        receiver.readStatus()
        messagesRepository.save(receiver)
        return MessagesReceiveResponse.from(receiver)
    }

    // 수정할 필요 있음
    @Transactional(readOnly = true)
    override fun receiverMessagesAll(pageable: Pageable, userId: Long): Page<MessagesReceiveResponse> {
        return messagesRepository.findAllByReceiverIdAndDeletedByReceiverFalseOrderByIdDesc(pageable, userId)
            .map { MessagesReceiveResponse.from(it) }
    }

    @Transactional
    override fun deleteSenderMessages(id: Long, userId: Long) {
        val messages = messagesRepository.findByIdOrNull(id) ?: throw ModelNotFoundException("Messages", id)
        if (messages.sender.id != userId) {
            throw NoSendMessagesException(id)
        }
        messages.deletedBySender()
        messagesRepository.save(messages)
    }

    @Transactional
    override fun deleteReceiverMessages(id: Long, userId: Long) {
        val messages = messagesRepository.findByIdOrNull(id) ?: throw ModelNotFoundException("Messages", id)
        if (messages.receiver.id != userId) {
            throw NoReceiverMessagesException(id)
        }
        messages.deletedByReceiver()
        messagesRepository.save(messages)
    }

    @Transactional
    override fun readMessagesByAdmin(id: Long, userId: Long): AdminMessagesSendResponse {
        // 받은 사람이 조회 가능하게 해야함. 그리고 위처럼 굳이 보낸 쪽지함은 안만들어도 됌 어드민이 조회가 가능할테니까? 이 부분은
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User", userId)
        val receiver = adminMessagesRepository.findByIdOrNull(id) ?: throw ModelNotFoundException("AdminMessages", id)
        if (receiver.deletedByReceiver) throw ModelNotFoundException("Messages", id)

        if (receiver.receiver.id != user.id) throw NoReceiverMessagesException(userId)

        receiver.readStatus()
        adminMessagesRepository.save(receiver)
        return AdminMessagesSendResponse.from(receiver)
    }

    @Transactional
    override fun readAllMessagesByAdmin(pageable: Pageable, userId: Long): Page<AdminMessagesSendResponse> {
        // 이것도 받은 사람이 조회 하게 해야함
        return adminMessagesRepository.findAllByReceiverIdAndDeletedByReceiverFalseOrderByIdDesc(pageable, userId)
            .map { AdminMessagesSendResponse.from(it) }
    }

    @Transactional
    override fun deleteUserByAdminMessages(id: Long, userId: Long) {
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User", userId)
        val admin = adminRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("Admin", userId)
        val adminMessages =
            adminMessagesRepository.findByIdOrNull(id) ?: throw ModelNotFoundException("AdminMessages", id)
        if (adminMessages.receiver.id != user.id && admin.id != userId) {
            throw NoReceiverMessagesException(id)
        }
        adminMessages.deletedByReceiver()
        adminMessagesRepository.save(adminMessages)
    }
}