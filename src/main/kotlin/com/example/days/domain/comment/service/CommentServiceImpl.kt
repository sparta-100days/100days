package com.example.days.domain.comment.service

import com.example.days.domain.comment.dto.request.CommentRequest
import com.example.days.domain.comment.dto.response.CommentResponse
import com.example.days.domain.comment.model.Comment
import com.example.days.domain.comment.repository.CommentRepository
import com.example.days.domain.post.dto.response.DeleteResponse
import com.example.days.domain.post.repository.PostRepository
import com.example.days.domain.user.repository.UserRepository
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
        val comemnt = commentRepository.findByIdOrNull(commentId) ?: throw IllegalArgumentException("해당 댓글이 존재하지 않습니다.")
        return CommentResponse.from(comemnt)
    }

    @Transactional
    override fun createComment(userId: UserPrincipal, postId: Long, request: CommentRequest): CommentResponse {
        val post = postRepository.findByIdOrNull(postId) ?: throw IllegalArgumentException("해당하는 게시글이 없습니다.")
        val user = userRepository.findByIdOrNull(userId.subject) ?: throw IllegalArgumentException("작성 권한이 없습니다.")

        val comment = Comment(comment = request.comment, userId = user, postId = post)
        commentRepository.save(comment)

        return CommentResponse.from(comment)
    }

    @Transactional
    override fun updateComment(userId: UserPrincipal, commentId: Long, request: CommentRequest): CommentResponse {
        userRepository.findByIdOrNull(userId.subject) ?: throw IllegalArgumentException("작성 권한이 없습니다.")
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw IllegalArgumentException("해당 댓글이 존재하지 않습니다.")

        if (comment.userId.id == userId.subject) {
            comment.comment = request.comment
        } else {
            throw IllegalArgumentException("회원님이 작성하신 댓글이 아닙니다.")
        }

        return commentRepository.save(comment).let { CommentResponse.from(it) }
    }

    @Transactional
    override fun deleteComment(userId: UserPrincipal, commentId: Long): DeleteResponse {
        val user = userRepository.findByIdOrNull(userId.subject) ?: throw IllegalArgumentException("작성 권한이 없습니다.")
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw IllegalArgumentException("해당 댓글이 존재하지 않습니다.")

        if (comment.userId.id == userId.subject) commentRepository.delete(comment)
        else throw throw IllegalArgumentException("회원님이 작성하신 댓글이 아닙니다.")

        return DeleteResponse("${user.nickname}님 댓글이 삭제 처리되었습니다.")
    }
}
