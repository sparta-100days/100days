package com.example.days.domain.resolution.dto.response

import com.example.days.domain.resolution.model.Resolution

data class SimpleResolutionResponse(
    val title: String = "",
    val category: String = "",
    val likeCount: Long = 0L,
){
    companion object{
        fun from(resolution: Resolution) = SimpleResolutionResponse(
            title = resolution.title,
            category = resolution.category.name,
            likeCount = resolution.likeCount
        )
    }
}