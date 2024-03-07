package com.example.days.domain.like.repository

import com.example.days.domain.like.model.Like
import com.example.days.domain.resolution.model.Resolution
import com.example.days.domain.user.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LikeRepository: JpaRepository<Like, Long> {
    fun existsByUserAndResolution(user: User, resolution: Resolution): Boolean
    fun findByUserAndResolution(user: User, resolution: Resolution): Like?
}