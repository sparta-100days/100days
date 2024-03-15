package com.example.days

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.scheduling.annotation.EnableScheduling

@EnableCaching
@EnableScheduling
@EnableJpaAuditing
@EnableJpaRepositories
@SpringBootApplication
//@EnableRedisRepositories
class Application

fun main(args: Array<String>) {
	runApplication<Application>(*args)
}
