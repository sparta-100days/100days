package com.example.days.domain.messages.controller

import com.example.days.domain.messages.dto.request.CreateMessageRequest
import com.example.days.domain.messages.dto.response.AdminMessagesSendResponse
import com.example.days.domain.messages.dto.response.MessageSendResponse
import com.example.days.domain.messages.dto.response.MessagesReceiveResponse
import com.example.days.domain.messages.service.MessagesService
import com.example.days.global.infra.security.UserPrincipal
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/messages")
class MessagesController(
    private val messagesService: MessagesService
) {

    @Operation(summary = "쪽지 작성")
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    fun createMessages(
        @Valid @RequestBody req: CreateMessageRequest, @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<MessageSendResponse> {
        val userId = userPrincipal.id
        return ResponseEntity.status(HttpStatus.CREATED).body(messagesService.createMessages(req, userId))
    }

    @Operation(summary = "보낸 쪽지 단건 조회")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/sender/{id}")
    fun sendMessages(
        @PathVariable id: Long,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<MessageSendResponse> {
        val userId = userPrincipal.id
        return ResponseEntity.status(HttpStatus.OK).body(messagesService.sendMessages(id, userId))
    }

    @Operation(summary = "보낸 쪽지 전체 조회")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/sender")
    fun sendMessagesAll(
        @PageableDefault(size = 10, sort = ["sentAt"]) pageable: Pageable,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<Page<MessageSendResponse>> {
        val userId = userPrincipal.id
        return ResponseEntity.status(HttpStatus.OK).body(messagesService.sendMessagesAll(pageable, userId))
    }

    @Operation(summary = "받은 쪽지 단건 조회")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/receiver/{id}")
    fun receiverMessages(
        @PathVariable id: Long,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<MessagesReceiveResponse> {
        val userId = userPrincipal.id
        return ResponseEntity.status(HttpStatus.OK).body(messagesService.receiverMessages(id, userId))
    }

    @Operation(summary = "받은 쪽지 전체 조회")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/receiver")
    fun receiverMessagesAll(
        @PageableDefault(size = 10, sort = ["sentAt"]) pageable: Pageable,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<Page<MessagesReceiveResponse>> {
        val userId = userPrincipal.id
        return ResponseEntity.status(HttpStatus.OK).body(messagesService.receiverMessagesAll(pageable, userId))
    }

    @Operation(summary = "보낸 쪽지 삭제")
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/sender/{id}")
    fun deleteSenderMessages(
        @PathVariable id: Long,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<Unit> {
        val userId = userPrincipal.id
        messagesService.deleteSenderMessages(id, userId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

    @Operation(summary = "받은 쪽지 삭제")
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/receiver/{id}")
    fun deleteReceiverMessages(
        @PathVariable id: Long,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<Unit> {
        val userId = userPrincipal.id
        messagesService.deleteReceiverMessages(id, userId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }


    @Operation(summary = "FROM 어드민 쪽지 단건 조회 Only 받은 유저만")
    @PreAuthorize("hasRole(hasRole('USER'))")
    @GetMapping("/admins/{id}")
    fun readMessagesByAdmin(
        @PathVariable id: Long,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<AdminMessagesSendResponse> {
        val userId = userPrincipal.id
        return ResponseEntity.status(HttpStatus.OK).body(messagesService.readMessagesByAdmin(id, userId))
    }

    @Operation(summary = "FROM 어드민 받은 쪽지 전체 조회")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/admins")
    fun readAllMessagesByAdmin(
        @PageableDefault(size = 10, sort = ["sentAt"]) pageable: Pageable,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<Page<AdminMessagesSendResponse>> {
        val userId = userPrincipal.id
        return ResponseEntity.status(HttpStatus.OK).body(messagesService.readAllMessagesByAdmin(pageable, userId))
    }

    @Operation(summary = "TO 유저 FROM 어드민 쪽지 삭제")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @DeleteMapping("/admins/{id}")
    fun deleteMessage(
        @PathVariable id: Long,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<AdminMessagesSendResponse> {
        val userId = userPrincipal.id
        messagesService.deleteUserByAdminMessages(id, userId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

}