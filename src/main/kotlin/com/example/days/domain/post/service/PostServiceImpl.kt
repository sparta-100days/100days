package com.example.days.domain.post.service

import com.example.days.domain.category.repository.CategoryRepository
import com.example.days.domain.comment.model.Comment
import com.example.days.domain.comment.repository.CommentRepository
import com.example.days.domain.post.dto.request.PostRequest
import com.example.days.domain.post.dto.response.DeleteResponse
import com.example.days.domain.post.dto.response.PostResponse
import com.example.days.domain.post.dto.response.PostWithCommentResponse
import com.example.days.domain.post.model.Post
import com.example.days.domain.post.model.PostType
import com.example.days.domain.post.repository.PostRepository
import com.example.days.domain.resolution.repository.ResolutionRepository
import com.example.days.domain.user.repository.UserRepository
import com.example.days.global.infra.security.UserPrincipal
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostServiceImpl(
    private val userRepository: UserRepository,
    private val postRepository: PostRepository,
    private val resolutionRepository: ResolutionRepository,
    private val categoryRepository: CategoryRepository,
    private val commentRepository: CommentRepository
) : PostService {

    // post 전체조회 (내림차순), comment x
    override fun getAllPostList(): List<PostResponse> {
        return postRepository.findAll().sortedByDescending { it.createdAt }.map { PostResponse.from(it) }
    }

    // post 개별조회, comment o
    @Transactional
    override fun getPostById(postId: Long): PostWithCommentResponse {
        val post = postRepository.findByIdOrNull(postId) ?: throw IllegalArgumentException("해당하는 게시글이 없습니다.")
        val comments: List<Comment> = commentRepository.findByPostId(post)
        post.comments.addAll(comments)

        return PostWithCommentResponse.from(post)
    }

    // post 작성 > 데일리 체크에서 달성도 체크 후 이쪽으로 넘어옴
    @Transactional
    override fun createPost(userId: UserPrincipal,
                            categoryId: Long,
                            resolutionId: Long,
                            type: PostType,
                            request: PostRequest
    ): PostResponse {
        val user = userRepository.findByIdOrNull(userId.subject) ?: throw IllegalArgumentException("작성 권한이 없습니다.")
        val category = categoryRepository.findByIdOrNull(categoryId) ?: throw IllegalArgumentException("게시글의 분류가 없습니다.")
        val resolution = resolutionRepository.findByIdOrNull(resolutionId) ?: throw IllegalArgumentException("게시글이 속한 목표가 없습니다.")
        val post = Post(
            title = request.title,
            content = request.content,
            imageUrl = request.imageUrl,
            type = type,
            userId = user,
            resolutionId = resolution,
            categoryId = category
        )
            // check 로 선택하면 제목만 입력가능, 나머지는 입력 x
            if (type == PostType.CHECK) {
                post.content = ""
                post.imageUrl = ""
            }

        return postRepository.save(post).let { PostResponse.from(post) }
    }

    // post 수정
    @Transactional
    override fun updatePost(userId: UserPrincipal, type: PostType, postId: Long, request: PostRequest): PostResponse {
        userRepository.findByIdOrNull(userId.subject) ?: throw IllegalArgumentException("작성 권한이 없습니다.")
        val post = postRepository.findByIdOrNull(postId) ?: throw IllegalArgumentException("게시글이 존재하지 않습니다.")

        // 작성 포스트 타입 확인
        if (type == post.type) {
            // 작성자 확인
            if (post.userId?.id == userId.subject) {
                // 작성 타입별 입력 폼 구분
                if (post.type == PostType.CHECK) {
                    val (title) = request
                    post.title = title
                    post.content = ""
                    post.imageUrl = ""

                } else if (post.type == PostType.POST) {
                    val (title, content, imageUrl) = request
                    post.title = title
                    post.content = content
                    post.imageUrl = imageUrl
                }

            } else {
                throw IllegalArgumentException("회원님이 작성하신 게시글이 아닙니다.")
            }
        } else {
            throw IllegalArgumentException("작성 포스트의 타입이 일치하지 않습니다.")
        }

        return postRepository.save(post).let { PostResponse.from(post) }
    }

    // post 삭제
    @Transactional
    override fun deletePost(userId: UserPrincipal, postId: Long): DeleteResponse {
        val user = userRepository.findByIdOrNull(userId.subject) ?: throw IllegalArgumentException("작성 권한이 없습니다.")
        val post = postRepository.findByIdOrNull(postId) ?: throw IllegalArgumentException("해당 게시글이 존재하지 않습니다.")

        // 작성자 확인
        if (post.userId?.id == userId.subject) {
            postRepository.delete(post)
        } else {
            throw IllegalArgumentException("회원님이 작성하신 게시글이 아닙니다.")
        }

        return DeleteResponse("${user.nickname} 님 게시글이 삭제 처리되었습니다.")
    }
}