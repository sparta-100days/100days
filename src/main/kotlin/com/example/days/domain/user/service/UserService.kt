package com.example.days.domain.user.service

import com.example.days.domain.user.dto.request.EmailRequest
import com.example.days.domain.user.dto.request.LoginRequest
import com.example.days.domain.user.dto.request.SignUpRequest
import com.example.days.domain.user.dto.response.ChangePasswordResponse
import com.example.days.domain.user.dto.response.EmailResponse
import com.example.days.domain.user.dto.response.LoginResponse
import com.example.days.domain.user.dto.response.SignUpResponse

interface UserService {

    fun login(request: LoginRequest): LoginResponse

    fun signUp(request: SignUpRequest): SignUpResponse

    fun searchUserEmail(nickname: String): List<EmailResponse> // 이메일 찾기 ~@~

    fun changeUserPassword(request: EmailRequest): ChangePasswordResponse // 비밀번호 분실 시 변경 ~@~
}