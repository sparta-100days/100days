package com.example.days.domain.post.dto.request

data class PostRequest(
    val title: String,
    val content: String,
    val imageUrl: String
)