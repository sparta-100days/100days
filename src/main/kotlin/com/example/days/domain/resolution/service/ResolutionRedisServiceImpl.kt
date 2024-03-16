package com.example.days.domain.resolution.service

import com.example.days.domain.resolution.dto.response.SearchLogRedisResponse
import com.example.days.domain.resolution.dto.response.SearchResponse
import com.example.days.domain.resolution.repository.ResolutionRepository
import com.example.days.domain.user.repository.UserRepository
import com.example.days.global.common.exception.NotHaveSearchException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service("RedisResolutionV2")
class ResolutionRedisServiceImpl(
    private val resolutionRepository: ResolutionRepository,
    private val redisTemplate: RedisTemplate<String, SearchLogRedisResponse>,
    private val userRepository: UserRepository
): ResolutionRedisService {
    @Cacheable(key = "#title", cacheNames = ["title"], condition = "#title != null")
    override fun searchByResolution(title: String, pageable: Pageable): Page<SearchResponse> {
        return resolutionRepository.searchByTitle(title, pageable)
    }

    override fun saveRecentSearchLog(title: String, userId: Long?){
        val now = LocalDateTime.now()

        userId?.let {
            val key: String = "SearchLog${userId}"
            val value: SearchLogRedisResponse = SearchLogRedisResponse(title = title, createdAt = now.toString())

            val size: Long? = redisTemplate.opsForList().size(key)
            if (size == 10L) {
                redisTemplate.opsForList().rightPop(key)
            }
            redisTemplate.opsForList().leftPush(key, value)
        }
    }

    override fun findRecentSearchLog(userId: Long): List<SearchLogRedisResponse> {

        val key = "SearchLog${userId}"
        val logs: List<SearchLogRedisResponse> = redisTemplate.opsForList().range(key, 0, -1) ?: emptyList()

        val objectMapper = ObjectMapper()
        objectMapper.writeValueAsString(SearchLogRedisResponse)

        return logs.map { it }
    }

    override fun deleteRecentSearchLog(userId: Long, title: String, createdAt: LocalDateTime) {

        val key = "SearchLog${userId}"
        val value: SearchLogRedisResponse = SearchLogRedisResponse(title = title, createdAt = createdAt.toString())

        val count = redisTemplate.opsForList().remove(key, 1, value)

        if (count == 0L) {
            throw NotHaveSearchException("")
        }
    }
}


