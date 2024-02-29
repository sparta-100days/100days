package com.example.days.domain.comment.service

import com.example.days.domain.comment.dto.request.CommentRequest
import com.example.days.domain.comment.dto.response.CommentResponse

interface CommentService {
    fun getCommentById(postId: Long, commentId: Long): CommentResponse

    fun creatComment(postId: Long,request: CommentRequest) : CommentResponse

    fun updateComment(postId: Long, commentId: Long, request: CommentRequest) : CommentResponse

    fun deleteComment(postId: Long, commentId: Long)
}