package com.example.days.domain.messages.service

import com.example.days.domain.messages.dto.request.CreateMessageRequest
import com.example.days.domain.messages.dto.response.AdminMessagesSendResponse
import com.example.days.domain.messages.dto.response.MessageSendResponse
import com.example.days.domain.messages.dto.response.MessagesReceiveResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface MessagesService {

    //유저 쪽지 작성(유저)
    fun createMessages(req: CreateMessageRequest, userId: Long): MessageSendResponse

    // 보낸 쪽지 단건 조회(유저)
    fun sendMessages(id: Long, userId: Long): MessageSendResponse

    // 보낸 쪽지 전체 조회(유저)
    fun sendMessagesAll(pageable: Pageable, userId: Long): Page<MessageSendResponse>

    // 받은 쪽지 단건 조회(유저)
    fun receiverMessages(receiverId: Long, userId: Long): MessagesReceiveResponse

    //받은 쪽지 전체 조회(유저)
    fun receiverMessagesAll(pageable: Pageable, userId: Long): Page<MessagesReceiveResponse>

    // 보낸 쪽지 삭제(유저)
    fun deleteSenderMessages(senderId: Long, userId: Long)

    // 받은 쪽지 삭제(유저)
    fun deleteReceiverMessages(receiverId: Long, userId: Long)

    // 어드민 쪽지 단건 조회 (유저)
    fun readMessagesByAdmin(id: Long, userId: Long): AdminMessagesSendResponse

    // 어드민 쪽지 전체 조회 (유저)
    fun readAllMessagesByAdmin(pageable: Pageable, userId: Long): Page<AdminMessagesSendResponse>

    // 어드민 쪽지 삭제(어드민 or 유저)
    fun deleteUserByAdminMessages(id: Long, userId: Long)


}