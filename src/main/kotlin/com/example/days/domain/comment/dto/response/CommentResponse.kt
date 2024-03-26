package com.example.days.domain.comment.dto.response

import com.example.days.domain.comment.model.Comment
import java.time.LocalDateTime

data class CommentResponse(
    val commentId: Long,
    val comment: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun from(comment: Comment): CommentResponse {
            return CommentResponse(
                comment.id!!,
                comment.comment,
                comment.createdAt,
                comment.updatedAt
            )
        }
    }
}
