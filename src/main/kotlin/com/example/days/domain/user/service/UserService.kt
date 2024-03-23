package com.example.days.domain.user.service

import com.example.days.domain.mail.dto.request.EmailRequest
import com.example.days.domain.mail.dto.response.EmailResponse
import com.example.days.domain.oauth2.client.kakao.dto.KakaoUserInfoResponse
import com.example.days.domain.user.dto.request.LoginRequest
import com.example.days.domain.user.dto.request.ModifyInfoRequest
import com.example.days.domain.user.dto.request.SignUpRequest
import com.example.days.domain.user.dto.request.UserPasswordRequest
import com.example.days.domain.user.dto.response.LoginResponse
import com.example.days.domain.user.dto.response.ModifyInfoResponse
import com.example.days.domain.user.dto.response.SignUpResponse
import com.example.days.domain.user.model.User
import com.example.days.global.infra.security.UserPrincipal

interface UserService {

    fun login(request: LoginRequest): LoginResponse

    fun signUp(request: SignUpRequest): SignUpResponse

    fun searchUserEmail(nickname: String): List<EmailResponse>

    fun changeUserPassword(request: EmailRequest)

    fun getInfo(userId: UserPrincipal): ModifyInfoResponse

    fun modifyInfo(userId: UserPrincipal, request: ModifyInfoRequest): ModifyInfoResponse

    fun withdraw(userId: UserPrincipal, request: UserPasswordRequest)

    fun passwordChange(userId: UserPrincipal, request: UserPasswordRequest)

    // 소셜 로그인 관련 처리, 있으면 조회 없으면 가입
    fun registerIfAbsent(userInfo: KakaoUserInfoResponse): User
}