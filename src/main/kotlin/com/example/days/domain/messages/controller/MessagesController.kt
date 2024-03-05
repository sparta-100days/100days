package com.example.days.domain.messages.controller

import com.example.days.domain.messages.dto.request.CreateMessageRequest
import com.example.days.domain.messages.dto.response.MessageResponse
import com.example.days.domain.messages.service.MessagesService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/messages")
class MessagesController(
    private val messagesService: MessagesService
) {

    @PostMapping
    fun createMessages(req: CreateMessageRequest): ResponseEntity<MessageResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(messagesService.createMessages(req))
    }

    @GetMapping("/sender/{senderId}")
    fun sendMessages(@PathVariable senderId: Long): ResponseEntity<MessageResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(messagesService.sendMessages(senderId))
    }

    @GetMapping("/sender")
    fun sendMessagesAll(): ResponseEntity<List<MessageResponse>> {
        return ResponseEntity.status(HttpStatus.OK).body(messagesService.sendMessagesAll())
    }

    @GetMapping("/receiver/{receiverId}")
    fun receiverMessages(@PathVariable receiverId: Long): ResponseEntity<MessageResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(messagesService.receiverMessages(receiverId))
    }

    @GetMapping("/receiver")
    fun receiverMessagesAll(): ResponseEntity<List<MessageResponse>> {
        return ResponseEntity.status(HttpStatus.OK).body(messagesService.receiverMessagesAll())
    }

    @DeleteMapping("/sender/{senderId}")
    fun deleteSenderMessages(@PathVariable senderId: Long): ResponseEntity<Unit> {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

    @DeleteMapping("/receiver/{receiverId}")
    fun deleteReceiverMessages(@PathVariable receiverId: Long): ResponseEntity<Unit> {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

}