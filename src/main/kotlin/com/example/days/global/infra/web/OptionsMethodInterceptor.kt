package com.example.days.global.infra.web

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

@Component
class OptionsMethodInterceptor : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (request.method == "OPTIONS") {
            // OPTIONS 메소드에 대한 사전 처리 로직
            return false // 요청 처리를 중단하고 싶은 경우 false 반환
        }
        return true // 그 외의 경우 계속해서 요청 처리 진행
    }
}
