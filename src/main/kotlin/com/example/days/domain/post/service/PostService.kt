package com.example.days.domain.post.service

import com.example.days.domain.post.dto.request.PostRequest
import com.example.days.domain.post.dto.response.DeleteResponse
import com.example.days.domain.post.dto.response.PostListResponse
import com.example.days.domain.post.dto.response.PostResponse
import com.example.days.domain.post.dto.response.PostWithCommentResponse
import com.example.days.domain.post.model.PostType
import com.example.days.global.infra.security.UserPrincipal

interface PostService {

    // post 전체조회 (목록)
    fun getAllPostList(): List<PostListResponse>

    // post 단건조회
    fun getPostById(postId: Long): PostWithCommentResponse

    // post 작성, 수정, 삭제
    fun createPost(userId: UserPrincipal, categoryId: Long, resolutionId: Long, type: PostType, request: PostRequest): PostResponse
    fun updatePost(userId: UserPrincipal, type: PostType, postId: Long, request: PostRequest): PostResponse
    fun deletePost(userId: UserPrincipal, postId: Long): DeleteResponse

}