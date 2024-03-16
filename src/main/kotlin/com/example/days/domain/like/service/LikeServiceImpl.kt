package com.example.days.domain.like.service

import com.example.days.domain.like.dto.request.LikeRequest
import com.example.days.domain.like.dto.response.LikeResponse
import com.example.days.domain.like.repository.LikeRepository
import com.example.days.domain.resolution.dto.response.SimpleResolutionResponse
import com.example.days.domain.resolution.repository.ResolutionRepository
import com.example.days.domain.user.repository.UserRepository
import com.example.days.global.common.exception.common.LikeAlreadyProcessedException
import com.example.days.global.common.exception.common.ModelNotFoundException
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.repository.findByIdOrNull
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LikeServiceImpl(
    private val userRepository: UserRepository,
    private val resolutionRepository: ResolutionRepository,
    private val likeRepository: LikeRepository,
    private val redisTemplate: RedisTemplate<String, Any>,
): LikeService {
    @Transactional
    override fun insertLike(request: LikeRequest) {
        val user = userRepository.findByIdOrNull(request.userId) ?: throw ModelNotFoundException("User", request.userId)
        val resolution = resolutionRepository.findByIdOrNull(request.resolutionId)
            ?: throw ModelNotFoundException("Resolution", request.resolutionId)
        if (!likeRepository.existsByUserAndResolution(user, resolution)){
            resolution.updateLikeCount(true)
            likeRepository.save(LikeResponse.from(user, resolution))
        }
        else throw LikeAlreadyProcessedException()
    }

    @Transactional
    override fun deleteLike(request: LikeRequest) {
        val user = userRepository.findByIdOrNull(request.userId) ?: throw ModelNotFoundException("User", request.userId)
        val resolution = resolutionRepository.findByIdOrNull(request.resolutionId)
            ?: throw ModelNotFoundException("Resolution", request.resolutionId)
        val canceledLike = likeRepository.findByUserAndResolution(user, resolution) ?: throw LikeAlreadyProcessedException()

        resolution.updateLikeCount(false)
        likeRepository.delete(canceledLike)
    }


}