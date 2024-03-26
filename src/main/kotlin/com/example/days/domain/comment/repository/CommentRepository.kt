package com.example.days.domain.comment.repository

import com.example.days.domain.comment.model.Comment
import com.example.days.domain.post.model.Post
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository : JpaRepository<Comment, Long> {
    fun findByPostId(post: Post): List<Comment>
}
