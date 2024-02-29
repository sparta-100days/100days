package com.example.days.domain.admin.service

import com.example.days.domain.admin.dto.request.SignUpAdminRequest
import com.example.days.domain.admin.dto.response.AdminResponse

interface AdminService {
    // 어드민 회원가입 이것도 임시적으로 만듦
    fun adminSignup(req: SignUpAdminRequest): AdminResponse

    // 유저 전체 조회, 유저 부분 추가 전이므로 어드민 response로 대체
    fun getAllUser(): List<AdminResponse>

    // 유저 밴 기능, 유저 부분 추가 전이므로 어드민 response로 대체
    fun userBanByAdmin()

    // 어드민 밴 기능, 유저 부분 추가 전이므로 어드민 response로 대체
    fun adminBanByAdmin(adminId: Long)

}