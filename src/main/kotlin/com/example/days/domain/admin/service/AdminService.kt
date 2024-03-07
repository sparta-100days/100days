package com.example.days.domain.admin.service

import com.example.days.domain.admin.dto.request.SignUpAdminRequest
import com.example.days.domain.admin.dto.request.UserBanRequest
import com.example.days.domain.admin.dto.response.AdminResponse
import com.example.days.domain.user.dto.response.UserResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface AdminService {
    // 어드민 회원가입 이것도 임시적으로 만듦
    fun adminSignup(req: SignUpAdminRequest): AdminResponse

    // 유저 전체 조회, 페이지네이션으로 처리
    fun getAllUser(pageable: Pageable): Page<UserResponse>

    // 유저 밴 기능
    fun userBanByAdmin(userId: Long, req: UserBanRequest): String

    // 유저 삭제 기능
    fun userDeleteByAdmin(userId: Long)

    // 어드민 밴 기능
    fun adminBanByAdmin(adminId: Long)

}