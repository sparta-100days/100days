package com.example.days.domain.comment.controller

import com.example.days.domain.comment.dto.request.CommentRequest
import com.example.days.domain.comment.dto.response.CommentResponse
import com.example.days.domain.comment.service.CommentService
import com.example.days.global.infra.security.UserPrincipal
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/post/{postId}/comments")
@RestController
class CommentController(
    private val commentService: CommentService
) {

    @Operation(summary = "코멘트 단건조회")
    @GetMapping("/{commentId}")
    fun getCommentById(
        @PathVariable postId: Long,
        @PathVariable commentId: Long
    ): ResponseEntity<CommentResponse>{
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getCommentById(commentId))
    }

    @Operation(summary = "코멘트 작성")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    fun creatComment(
        @AuthenticationPrincipal userId: UserPrincipal,
        @PathVariable postId: Long,
        @RequestBody request: CommentRequest
    ) : ResponseEntity<CommentResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.createComment(userId, postId, request))
    }

    @Operation(summary = "코멘트 수정")
    @PutMapping("/{commentId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    fun updateComment(
        @AuthenticationPrincipal userId: UserPrincipal,
        @PathVariable commentId: Long,
        @RequestBody request: CommentRequest,
        @PathVariable postId: Long
    ) : ResponseEntity<CommentResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.updateComment(userId, commentId, request))
    }

    @Operation(summary = "코멘트 삭제")
    @DeleteMapping("/{commentId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    fun deleteComment(
        @PathVariable postId: Long,
        @AuthenticationPrincipal userId: UserPrincipal,
        @PathVariable commentId: Long
    ) : ResponseEntity<Unit> {
        commentService.deleteComment(userId, commentId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}