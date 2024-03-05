package com.example.days.domain.post.service

import com.example.days.domain.post.dto.request.PostRequest
import com.example.days.domain.post.dto.response.PostResponse
import com.example.days.domain.post.model.Post
import com.example.days.domain.post.repository.PostRepository
import com.example.days.global.common.exception.ModelNotFoundException
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PostServiceImpl(
    private val postRepository: PostRepository
) : PostService{

    override fun getPostList(): List<PostResponse> {
        return postRepository.findAll().map {PostResponse.from(it)}
    }

    override fun getPost(postId: Long): PostResponse {
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post",postId)
        return PostResponse.from(post)
    }

    @Transactional
    override fun creatPost(request: PostRequest): PostResponse {
        return Post(
            title = request.title,
            content = request.content,
            image_Url = request.image_Url,
            postType = request.postType
        ).let {
            postRepository.save(it)
        }.let {
            PostResponse.from(it)
        }
    }

    @Transactional
    override fun updatePost(postId: Long, request: PostRequest): PostResponse {
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post",postId)

        post.updatePost(request)

        return postRepository.save(post)
            .let {
                PostResponse.from(it)
            }
    }

    @Transactional
    override fun deletePost(postId: Long) {
        /*val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post",postId)

        post.deletePost()

        postRepository.save(post)*/
    TODO()
    }
}