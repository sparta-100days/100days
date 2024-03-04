package com.example.days.domain.comment.service

import com.example.days.domain.admin.exception.ModelNotFoundException
import com.example.days.domain.comment.dto.request.CommentRequest
import com.example.days.domain.comment.dto.response.CommentResponse
import com.example.days.domain.comment.model.Comment
import com.example.days.domain.comment.repository.CommentRepository
import com.example.days.domain.post.repository.PostRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CommentServiceImpl(
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository
) : CommentService {

    override fun getCommentById(postId: Long, commentId: Long): CommentResponse {
        val comment = commentRepository.findByPostIdAndId(postId, commentId)
            ?: throw ModelNotFoundException("Comment", commentId)

        return commentRepository.save(comment)
            .let { CommentResponse.from(it) }
    }

    @Transactional
    override fun creatComment(postId: Long, request: CommentRequest): CommentResponse {
        val post = postRepository.findByIdOrNull(postId
        ) ?: throw ModelNotFoundException("Post",postId)

        if (commentRepository.existsByPostId(post.id!!)) {
            throw IllegalStateException("already written a post")
        }

        return Comment(
            comment = request.comment,
            post = post
        ).let {
            commentRepository.save(it)
        }.let {
            CommentResponse.from(it)
        }
    }

    @Transactional
    override fun updateComment(postId: Long, commentId: Long, request: CommentRequest): CommentResponse {
        val comment = commentRepository.findByPostIdAndId(postId, commentId) ?: throw ModelNotFoundException("Comment",commentId)

        comment.updateComment(request)

        return commentRepository.save(comment)
            .let { CommentResponse.from(it) }
    }

    @Transactional
    override fun deleteComment(postId: Long, commentId: Long) {
        /*val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("Comment", commentId)

        comment.deleteComment()

        postRepository.save(post)*/
    TODO()
    }
}