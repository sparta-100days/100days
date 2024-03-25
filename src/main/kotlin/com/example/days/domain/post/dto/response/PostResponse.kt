package com.example.days.domain.post.dto.response

import com.example.days.domain.post.model.Post
import java.time.LocalDateTime

data class PostResponse(
    val id: Long,
    val title: String,
    val content: String,
    val imageUrl: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime

) {

    companion object {
        fun from(post: Post): PostResponse {
            return PostResponse(
                post.id!!,
                post.title,
                post.content,
                post.imageUrl,
                post.createdAt,
                post.updatedAt
            )
        }
    }
}