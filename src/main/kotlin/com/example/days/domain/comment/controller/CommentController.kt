package com.example.days.domain.comment.controller

import com.example.days.domain.comment.dto.request.CommentRequest
import com.example.days.domain.comment.dto.response.CommentResponse
import com.example.days.domain.comment.service.CommentService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/post/{postId}/comments")
@RestController
class CommentController(
    private val commentService: CommentService
) {

    @GetMapping("/{commentId}")
    fun getCommentById(
        @PathVariable postId: Long,
        @PathVariable commentId: Long
    ): ResponseEntity<CommentResponse>{
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(commentService.getCommentById(postId, commentId))
    }

    @PostMapping
    fun creatComment(
        @PathVariable postId: Long,
        @RequestBody request: CommentRequest
    ) : ResponseEntity<CommentResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(commentService.creatComment(postId, request))
    }

    @PutMapping("/{commentId}")
    fun updateComment(
        @PathVariable postId: Long,
        @PathVariable commentId: Long,
        @RequestBody request: CommentRequest
    ) : ResponseEntity<CommentResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(commentService.updateComment(postId, commentId, request))
    }

    @DeleteMapping("/{commentId}")
    fun deleteComment(
        @PathVariable postId: Long,
        @PathVariable commentId: Long
    ) : ResponseEntity<Unit> {
        //commentService.deleteComment(postId, commentId)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }
}