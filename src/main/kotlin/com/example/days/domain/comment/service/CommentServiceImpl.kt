package com.example.days.domain.comment.service

import com.example.days.domain.comment.dto.request.CommentRequest
import com.example.days.domain.comment.dto.response.CommentResponse
import com.example.days.domain.comment.model.Comment
import com.example.days.domain.comment.repository.CommentRepository
import com.example.days.domain.post.dto.response.DeleteResponse
import com.example.days.domain.post.repository.PostRepository
import com.example.days.domain.user.repository.UserRepository
import com.example.days.global.common.exception.auth.PermissionDeniedException
import com.example.days.global.common.exception.common.ModelNotFoundException
import com.example.days.global.common.exception.user.UserNotFoundException
import com.example.days.global.infra.security.UserPrincipal
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentServiceImpl(
    private val userRepository: UserRepository,
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository
) : CommentService {

    override fun getCommentById(commentId: Long): CommentResponse {
        val comemnt = commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("댓글", commentId)
        return CommentResponse.from(comemnt)
    }

    @Transactional
    override fun createComment(userId: UserPrincipal, postId: Long, request: CommentRequest): CommentResponse {
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("게시글", postId)
        val user = userRepository.findByIdOrNull(userId.id) ?: throw UserNotFoundException()

        val comment = Comment(comment = request.comment, userId = user, postId = post)
        commentRepository.save(comment)

        return CommentResponse.from(comment)
    }

    @Transactional
    override fun updateComment(userId: UserPrincipal, commentId: Long, request: CommentRequest): CommentResponse {
        userRepository.findByIdOrNull(userId.id) ?: throw UserNotFoundException()
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("댓글", commentId)

        if (comment.userId.id == userId.id) {
            comment.comment = request.comment
        } else {
            throw PermissionDeniedException()
        }

        return commentRepository.save(comment).let { CommentResponse.from(it) }
    }

    @Transactional
    override fun deleteComment(userId: UserPrincipal, commentId: Long): DeleteResponse {
        val user = userRepository.findByIdOrNull(userId.id) ?: throw UserNotFoundException()
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("댓글", commentId)

        if (comment.userId.id == userId.id) commentRepository.delete(comment)
        else throw PermissionDeniedException()

        return DeleteResponse("${user.nickname}님 댓글이 삭제 처리되었습니다.")
    }
}
