package com.example.days.domain.like.service

import com.example.days.domain.like.dto.request.LikeRequest
import org.springframework.stereotype.Service
interface LikeService {
    fun insertLike(request: LikeRequest)
    fun deleteLike(request: LikeRequest)
}