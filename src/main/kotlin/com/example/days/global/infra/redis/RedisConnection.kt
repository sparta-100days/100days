package com.example.days.global.infra.redis

import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration

@Configuration
@EnableCaching
class RedisConnection(
    @Value("\${spring.data.redis.host}")
    private val host: String,
    @Value("\${spring.data.redis.port}")
    private val port: Int,
    @Value("\${spring.data.redis.password}")
    private val password: String
) {
    // Redis 와의 연결을 위한 설정.
    // Lettuce: Redis 클라이언트 라이브러리. Redis 를 비동기 형태로 사용하기 위한 팩토리를 지원한다.
    // 기본적으로 비동기 작업을 지원하지만 동기적으로도 가능하다고 함.
    // RedisStandaloneConfiguration: Redis를 스탠드얼론 모드로 설정한 후 서버의 설정을 정의
    // RedisStandaloneConfiguration: Redis 를 스탠드얼론 모드로 설정한 후 서버의 설정을 정의
    // Redis 의 모드는 여러가지가 있다고 합니다. 클러스터, 센티넬, 복제, 파이프라인 등등...
    @Bean
    fun lettuceConnectionFactory() = RedisStandaloneConfiguration().let {
            it.hostName = host
            it.port = port
            it.setPassword(password)
            it
        }.let { LettuceConnectionFactory(it) }


    // 캐시 매니져로 Redis 를 설정할 때 필요한 configuration
    // RedisCacheManager 는 spring 에서 제공하는 클래스임!
    // Redis 를 캐시 스토리지로 사용할 때 필요한 구성과 동작을 관리할 수 있음.
    // builder 안에 lettuceConnectionFactory 를 넣어서 Redis 서버에 연결함.
    // .cacheDefaults(RedisCacheConfiguration.defaultCacheConfig()
    // -> 기본 캐시 구성을 설정하는 단계. defaultCacheConfig 는 기본적으로 설정된 캐시 구성이 들어가 있음.
    // 직렬화 방식, TTL 설정, prefix, null 값 캐싱 여부 등이 기본적으로 설정되어있음.
    // 밑에 코드에선 TTL 을 10분으로 설정함. 기본값은 무제한!

    // 제가 구현한 랭킹 조회에서 Redis 는 캐시 기능을 사용하고 있지 않습니다!
    // 임시로 간단하게 작성했습니다. 나중에 사용하실 때 더 자세한 설정을 추가해 주시면 됩니다!
    
    @Bean
    fun redisCacheManager(): RedisCacheManager{
        return RedisCacheManager.builder(lettuceConnectionFactory())
            .cacheDefaults(
                RedisCacheConfiguration.defaultCacheConfig()
                    .entryTtl(Duration.ofMinutes(10))
            )
            .build()
    }


    // RedisTemplate: Redis 의 데이터 엑세스 코드를 쉽게 작성할 수 있도록 Spring 에서 제공하는 클래스.
    // 아래 코드는 Redis 명령을 수행하는데 필요한 직렬화와 커넥션을 관리하는 코드.

    // 일단 직렬화 방식을 StringRedisSerializer 으로 설정했습니다. 후에 직렬화 방식에 대해 좀 더 공부할 필요가 있어보입니다.
    @Bean
    fun redisTemplate(): RedisTemplate<*,*>{
        val genericJackson2JsonRedisSerializer = GenericJackson2JsonRedisSerializer()
        return RedisTemplate<Any, Any>().apply{
            this.connectionFactory = lettuceConnectionFactory()
            this.keySerializer = StringRedisSerializer()
            this.valueSerializer = genericJackson2JsonRedisSerializer
            this.hashKeySerializer = StringRedisSerializer()
            this.hashValueSerializer = genericJackson2JsonRedisSerializer
        }
    }
}