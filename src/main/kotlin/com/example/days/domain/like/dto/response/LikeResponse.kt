package com.example.days.domain.like.dto.response

import com.example.days.domain.like.model.Like
import com.example.days.domain.resolution.model.Resolution
import com.example.days.domain.user.model.User


data class LikeResponse(
    val user: User,
    val resolution: Resolution
){
    companion object{
        fun from(user: User, resolution: Resolution) = Like(
            user = user,
            resolution = resolution
        )
    }
}
