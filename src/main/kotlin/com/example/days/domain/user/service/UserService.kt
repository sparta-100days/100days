package com.example.days.domain.user.service

import com.example.days.domain.user.dto.request.LoginRequest
import com.example.days.domain.user.dto.request.SignUpRequest
import com.example.days.domain.user.dto.response.LoginResponse
import com.example.days.domain.user.dto.response.SignUpResponse
import org.springframework.stereotype.Service

@Service
interface UserService {

    fun login(request: LoginRequest): LoginResponse

    fun signUp(request: SignUpRequest): SignUpResponse


}