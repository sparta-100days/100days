package com.example.days.domain.resolution.service

import com.example.days.domain.resolution.dto.response.SearchLogRedis
import com.example.days.domain.resolution.dto.response.SearchResponse
import com.example.days.domain.resolution.repository.ResolutionRepository
import com.example.days.domain.user.model.User
import com.example.days.domain.user.repository.UserRepository
import com.example.days.global.common.exception.ModelNotFoundException
import com.example.days.global.common.exception.NotHaveSearchException
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service("RedisResolutionV2")
class ResolutionRedisServiceImpl(
    private val resolutionRepository: ResolutionRepository,
    private val redisTemplate: RedisTemplate<String, SearchLogRedis>,
    private val userRepository: UserRepository
): ResolutionRedisService {
    @Cacheable(key = "#title", cacheNames = ["title"], condition = "#title != null")
    override fun searchByResolution(title: String, pageable: Pageable): Page<SearchResponse> {
        return resolutionRepository.searchByKeyword(title, pageable)
    }

    override fun saveRecentSearchLog(title: String, userId: Long){
        val user: User? = userRepository.findUserById(userId)
        val now = LocalDateTime.now().toString()

        // user가 null이 아닌 경우에만 작업을 진행합니다.
        user?.let {
            val key: String = "SearchLog${user.id}"
            // SearchLogRedis 객체를 생성하는 부분을 완성했습니다.
            val value: SearchLogRedis = SearchLogRedis(title = title, createdAt = now)

            val size: Long? = redisTemplate.opsForList().size(key)
            if (size == 10L) {
                redisTemplate.opsForList().rightPop(key)
            }
            redisTemplate.opsForList().leftPush(key, value)
        }
    }

    override fun findRecentSearchLog(userId: Long): List<SearchLogRedis>? {
        val user: User = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User",userId)

        val key = "SearchLog${user.id}"
        val logs: List<SearchLogRedis>? = redisTemplate.opsForList().range(
        key, 0, 9)

        return logs
    }

    override fun deleteRecentSearchLog(userId: Long, title: String) {
        val user: User = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User",userId)
        val now = LocalDateTime.now().toString()

        val key = "SearchLog${user.id}"
        val value: SearchLogRedis = SearchLogRedis(title = title, createdAt = now)

        val count = redisTemplate.opsForList().remove(key, 1, value)

        if (count == 0L) {
            throw NotHaveSearchException("")
        }
    }
}


