package com.example.days.global.infra.web

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {

//    override fun addFormatters(registry: FormatterRegistry) {
//
//    }

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**") // 모든 경로에 대해
            .allowedOrigins("http://localhost:8080") // 이 출처로부터의 요청만 허용
            .allowedMethods("*") // 모든 HTTP 메소드 허용
            .allowCredentials(true) // 쿠키를 포함한 요청 허용
    }
}