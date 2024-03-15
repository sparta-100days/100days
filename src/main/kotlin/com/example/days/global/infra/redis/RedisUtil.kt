package com.example.days.global.infra.redis

import com.example.days.domain.admin.repository.AdminRepository
import com.example.days.domain.user.model.RefreshToken
//import com.example.days.domain.user.repository.TokenRepository
import com.example.days.domain.user.repository.UserRepository
import org.springframework.data.redis.core.HashOperations
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.core.ValueOperations
import org.springframework.stereotype.Component
import java.time.Duration


@Component
class RedisUtil(
    private val stringRedisTemplate: StringRedisTemplate,
    private val redisTemplate: RedisTemplate<String, String>
) {

    val token = "tokens"

    fun getData(key: String): String? {
        val valueOperations: ValueOperations<String, String> = stringRedisTemplate.opsForValue()
        return valueOperations.get(key)
    }

    fun setData(key: String, value: String) {
        val valueOperations: ValueOperations<String, String> = stringRedisTemplate.opsForValue()
        valueOperations.set(key, value)
    }

    fun setDataExpire(key: String, value: String, duration: Long) {
        val valueOperations: ValueOperations<String, String> = stringRedisTemplate.opsForValue()
        val expireDuration: Duration = Duration.ofSeconds(duration)
        valueOperations.set(key, value, expireDuration)
    }

    fun deleteData(key: String) {
        stringRedisTemplate.delete(key)
    }

    fun saveRefreshToken(id: String, refreshToken: String) {
        redisTemplate.opsForHash<String, String>().put(id, refreshToken, token)
    }

    fun getRefreshToken(id: String): String? {
        return redisTemplate.opsForHash<String, String>().get(token, id)
    }
}