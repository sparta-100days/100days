package com.example.days.domain.user.service

import com.example.days.domain.user.dto.request.*
import com.example.days.domain.user.dto.response.*
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
}