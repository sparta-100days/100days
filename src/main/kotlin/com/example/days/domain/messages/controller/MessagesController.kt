package com.example.days.domain.messages.controller

import com.example.days.domain.messages.dto.request.CreateMessageRequest
import com.example.days.domain.messages.dto.response.MessageSendResponse
import com.example.days.domain.messages.dto.response.MessagesReceiveResponse
import com.example.days.domain.messages.service.MessagesService
import com.example.days.global.infra.security.UserPrincipal
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
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
    @PostMapping
    fun createMessages(
        @Valid @RequestBody req: CreateMessageRequest,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<MessageSendResponse> {
        val user = userPrincipal.id
        return ResponseEntity.status(HttpStatus.CREATED).body(messagesService.createMessages(req, user))
    }

    @Operation(summary = "보낸 쪽지 단건 조회")
    @PreAuthorize("#userPrincipal.id == #id")
    @GetMapping("/sender/{id}")
    fun sendMessages(@PathVariable id: Long, @AuthenticationPrincipal userPrincipal: UserPrincipal): ResponseEntity<MessageSendResponse> {
        val userId = userPrincipal.id
        return ResponseEntity.status(HttpStatus.OK).body(messagesService.sendMessages(id, userId))
    }

    @Operation(summary = "보낸 쪽지 전체 조회")
    @PreAuthorize("#userPrincipal.id == #id")
    @GetMapping("/sender")
    fun sendMessagesAll(@AuthenticationPrincipal userPrincipal: UserPrincipal): ResponseEntity<List<MessageSendResponse>> {
        val userId = userPrincipal.id
        return ResponseEntity.status(HttpStatus.OK).body(messagesService.sendMessagesAll(userId))
    }

    @Operation(summary = "받은 쪽지 단건 조회")
    @PreAuthorize("#userPrincipal.id == #id")
    @GetMapping("/receiver/{id}")
    fun receiverMessages(@PathVariable id: Long, @AuthenticationPrincipal userPrincipal: UserPrincipal): ResponseEntity<MessagesReceiveResponse> {
        val userId = userPrincipal.id
        return ResponseEntity.status(HttpStatus.OK).body(messagesService.receiverMessages(id, userId))
    }

    @Operation(summary = "받은 쪽지 전체 조회")
    @PreAuthorize("#userPrincipal.id == #id")
    @GetMapping("/receiver")
    fun receiverMessagesAll(@AuthenticationPrincipal userPrincipal: UserPrincipal): ResponseEntity<List<MessagesReceiveResponse>> {
        val userId = userPrincipal.id
        return ResponseEntity.status(HttpStatus.OK).body(messagesService.receiverMessagesAll(userId))
    }
    @Operation(summary = "보낸 쪽지 삭제")
    @PreAuthorize("#userPrincipal.id == #id")
    @DeleteMapping("/sender/{id}")
    fun deleteSenderMessages(@PathVariable id: Long, @AuthenticationPrincipal userPrincipal: UserPrincipal): ResponseEntity<Unit> {
        val userId = userPrincipal.id
        messagesService.deleteSenderMessages(id, userId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

    @Operation(summary = "받은 쪽지 삭제")
    @PreAuthorize("#userPrincipal.id == #id")
    @DeleteMapping("/receiver/{id}")
    fun deleteReceiverMessages(@PathVariable id: Long, @AuthenticationPrincipal userPrincipal: UserPrincipal): ResponseEntity<Unit> {
        val userId = userPrincipal.id
        messagesService.deleteReceiverMessages(id, userId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}