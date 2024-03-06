package com.example.days.domain.messages.controller

import com.example.days.domain.messages.dto.request.CreateMessageRequest
import com.example.days.domain.messages.dto.response.MessageResponse
import com.example.days.domain.messages.service.MessagesService
import com.example.days.domain.user.model.User
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/messages")
class MessagesController(
    private val messagesService: MessagesService
) {

    @PostMapping
    fun createMessages(@Valid @RequestBody req: CreateMessageRequest, @AuthenticationPrincipal user: User): ResponseEntity<MessageResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(messagesService.createMessages(req, user))
    }

    @GetMapping("/sender/{id}")
    fun sendMessages(@PathVariable id: Long, @AuthenticationPrincipal user: User): ResponseEntity<MessageResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(messagesService.sendMessages(id, user))
    }

    @GetMapping("/sender")
    fun sendMessagesAll(@AuthenticationPrincipal user: User): ResponseEntity<List<MessageResponse>> {
        return ResponseEntity.status(HttpStatus.OK).body(messagesService.sendMessagesAll(user))
    }

    @GetMapping("/receiver/{id}")
    fun receiverMessages(@PathVariable id: Long, @AuthenticationPrincipal user: User): ResponseEntity<MessageResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(messagesService.receiverMessages(id, user))
    }

    @GetMapping("/receiver")
    fun receiverMessagesAll(@AuthenticationPrincipal user: User): ResponseEntity<List<MessageResponse>> {
        return ResponseEntity.status(HttpStatus.OK).body(messagesService.receiverMessagesAll(user))
    }

    @DeleteMapping("/sender/{id}")
    fun deleteSenderMessages(@PathVariable id: Long, @AuthenticationPrincipal user: User): ResponseEntity<Unit> {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

    @DeleteMapping("/receiver/{id}")
    fun deleteReceiverMessages(@PathVariable id: Long, @AuthenticationPrincipal user: User): ResponseEntity<Unit> {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

}