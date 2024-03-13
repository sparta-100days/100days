package com.example.days.global.infra.redis

import com.example.days.global.support.MailType
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.core.ValueOperations
import org.springframework.stereotype.Component
import java.time.Duration


@Component
class RedisUtil(private val stringRedisTemplate: StringRedisTemplate) {

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
}