package com.example.days.domain.comment.service

import com.example.days.domain.comment.dto.request.CommentRequest
import com.example.days.domain.comment.dto.response.CommentResponse
import com.example.days.domain.post.dto.response.DeleteResponse
import com.example.days.global.infra.security.UserPrincipal

interface CommentService {

    // comment 조회 (단건)
    fun getCommentById(commentId: Long): CommentResponse

    // comment 작성, 수정, 삭제
    fun createComment(userId: UserPrincipal, postId: Long, request: CommentRequest): CommentResponse
    fun updateComment(userId: UserPrincipal, commentId: Long, request: CommentRequest): CommentResponse
    fun deleteComment(userId: UserPrincipal, commentId: Long): DeleteResponse

}
