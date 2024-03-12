package com.example.days.domain.like.service

import com.example.days.domain.like.dto.request.LikeRequest
import com.example.days.domain.like.dto.response.LikeResponse
import com.example.days.domain.like.repository.LikeRepository
import com.example.days.domain.resolution.repository.ResolutionRepository
import com.example.days.domain.user.repository.UserRepository
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.repository.findByIdOrNull
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
        val user = userRepository.findByIdOrNull(request.userId) ?: TODO("예외처리")
        val resolution = resolutionRepository.findByIdOrNull(request.resolutionId) ?: TODO("예외처리")
        if (!likeRepository.existsByUserAndResolution(user, resolution)){
            resolution.updateLikeCount(true)
            likeRepository.save(LikeResponse.from(user, resolution))
            redisTemplate.opsForZSet().incrementScore("ranking", resolution.id.toString(), 1.0)
        }
        else{
            TODO("이미 좋아요를 눌렀을 때")
        }
    }

    @Transactional
    override fun deleteLike(request: LikeRequest) {
        val user = userRepository.findByIdOrNull(request.userId) ?: TODO("예외처리")
        val resolution = resolutionRepository.findByIdOrNull(request.resolutionId) ?: TODO("예외처리")
        val canceledLike = likeRepository.findByUserAndResolution(user, resolution) ?: TODO("예외처리")

        resolution.updateLikeCount(false)
        likeRepository.delete(canceledLike)
        redisTemplate.opsForZSet().incrementScore("ranking", resolution.id.toString(), -1.0)

        val score = redisTemplate.opsForZSet().score("ranking", resolution.id.toString())
        if (score != null && score <= 0) {
            // 스코어가 0이하이면 Redis 에서 해당 데이터 삭제함
            redisTemplate.opsForZSet().remove("ranking", resolution.id.toString())
        }
    }
}