package com.example.days.domain.post.dto.response

import com.example.days.domain.comment.dto.response.CommentResponse
import com.example.days.domain.post.model.Post
import java.time.LocalDateTime

data class PostWithCommentResponse (
    val id: Long,
    val title: String,
    val content: String,
    val imageUrl: String,
    val createdAt : LocalDateTime,
    val updatedAt : LocalDateTime,
    val comments: List<CommentResponse>
) {

    companion object {
        fun from(post: Post): PostWithCommentResponse {
            val comments = post.comments.map { CommentResponse.from(it) }
            return PostWithCommentResponse(
                post.id!!,
                post.title,
                post.content,
                post.imageUrl,
                post.createdAt,
                post.updatedAt,
                comments
            )
        }
    }
}