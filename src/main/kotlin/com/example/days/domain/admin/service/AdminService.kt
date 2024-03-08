package com.example.days.domain.admin.service

import com.example.days.domain.admin.dto.request.LoginAdminRequest
import com.example.days.domain.admin.dto.request.SignUpAdminRequest
import com.example.days.domain.admin.dto.request.UserBanRequest
import com.example.days.domain.admin.dto.response.AdminResponse
import com.example.days.domain.admin.dto.response.LoginAdminResponse
import com.example.days.domain.user.dto.response.UserResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface AdminService {
    // 어드민 회원가입
    fun adminSignup(req: SignUpAdminRequest): AdminResponse

    // 어드민 로그인
    fun adminLogin(req: LoginAdminRequest): LoginAdminResponse

    // 유저 전체 조회, 페이지네이션으로 처리
    fun getAllUser(pageable: Pageable): Page<UserResponse>

    // 유저 밴 기능
    fun userBanByAdmin(userId: Long): String

    // 유저 삭제 기능
    fun userDeleteByAdmin(userId: Long)

    // 어드민 밴 기능
    fun adminBanByAdmin(adminId: Long)

}