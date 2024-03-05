package com.example.days.domain.like.dto.request

import com.example.days.domain.resolution.model.Resolution
import com.example.days.domain.user.model.User

data class LikeRequest(
    val userId: Long,
    val resolutionId: Long
)
