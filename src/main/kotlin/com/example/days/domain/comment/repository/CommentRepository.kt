package com.example.days.domain.comment.repository

import com.example.days.domain.comment.model.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository : JpaRepository<Comment, Long> {
    fun findByPostIdAndId(postId: Long , commentId: Long): Comment?

    fun existsByPostId(postId: Long): Boolean
}