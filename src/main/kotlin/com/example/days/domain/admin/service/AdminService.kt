package com.example.days.domain.admin.service

import com.example.days.domain.admin.dto.request.CategoryRequest
import com.example.days.domain.admin.dto.request.CategoryUpdateRequest
import com.example.days.domain.admin.dto.request.SignUpAdminRequest
import com.example.days.domain.admin.dto.response.AdminResponse
import com.example.days.domain.admin.dto.response.CategoryResponse
import com.example.days.domain.admin.model.Admin
import org.springframework.security.core.userdetails.User

interface AdminService {

    // 유저 전체 조회, 유저 부분 추가 전이므로 어드민 response로 대체
    fun getAllUser(): List<AdminResponse>

    // 목표 전체 조회, 목표 부분 추가 전이므로 어드민 response로 대체
    fun getResolution(): List<AdminResponse>

    // 유저 밴 기능, 유저 부분 추가 전이므로 어드민 response로 대체
    fun userBanByAdmin()

    // 어드민 밴 기능, 유저 부분 추가 전이므로 어드민 response로 대체
    fun adminBanByAdmin()

    // 카테고리 추가 기능, 조회는 admin에 넣는 것이 아닌것 같아서 나중에 다른 곳에 추가할 필요 있을듯?
    fun createCategory(req: CategoryRequest): CategoryResponse

    // 카테고리 수정 기능
    fun updateCategory(categoryId: Long, req: CategoryUpdateRequest): CategoryResponse

    // 카테고리 삭제 기능
    fun deleteCategory(categoryId: Long)

}