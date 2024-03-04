package com.example.days.domain.post.service

import com.example.days.domain.post.dto.request.PostRequest
import com.example.days.domain.post.dto.response.PostResponse

interface PostService {
    fun getPostList() : List<PostResponse>

    fun getPost(postId : Long) : PostResponse

    fun creatPost(request: PostRequest) : PostResponse

    fun updatePost(postId: Long, request: PostRequest) : PostResponse

    fun deletePost(postId: Long)
}