package com.example.days.domain.post.dto.request

import com.example.days.domain.post.model.PostType

data class PostRequest(
    val title : String,
    val content : String,
    val image_Url : String,
    val postType : PostType
)
