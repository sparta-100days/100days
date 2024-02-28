package com.example.days.domain.admin.service

import com.example.days.domain.admin.dto.request.CategoryRequest
import com.example.days.domain.admin.dto.request.CategoryUpdateRequest
import com.example.days.domain.admin.dto.request.SignUpAdminRequest
import com.example.days.domain.admin.dto.response.AdminResponse
import com.example.days.domain.admin.dto.response.CategoryResponse
import com.example.days.domain.admin.exception.ModelNotFoundException
import com.example.days.domain.admin.model.Admin
import com.example.days.domain.admin.model.Category
import com.example.days.domain.admin.repository.AdminRepository
import com.example.days.domain.admin.repository.CategoryRepository
import org.springframework.data.repository.findByIdOrNull

class AdminServiceImpl(
    private val adminRepository: AdminRepository,
    private val categoryRepository: CategoryRepository
) : AdminService {
    override fun adminSignup(req: SignUpAdminRequest): AdminResponse {
        return adminRepository.save(
            Admin(
                nickname = req.nickname,
                email = req.email,
                password = req.password
            )
        ).let { AdminResponse.from(it) }
    }

    override fun getAllUser(): List<AdminResponse> {
        TODO("Not yet implemented")
    }

    override fun getResolution(): List<AdminResponse> {
        TODO("Not yet implemented")
    }

    override fun userBanByAdmin() {
        TODO("Not yet implemented")
    }

    override fun adminBanByAdmin() {
        TODO("Not yet implemented")
    }

    override fun createCategory(req: CategoryRequest): CategoryResponse {
        val category = categoryRepository.save(
            Category(
                name = req.name,
                info = req.info
            )
        )
        return CategoryResponse.from(category)
    }

    override fun updateCategory(categoryId: Long, req: CategoryUpdateRequest): CategoryResponse {
        val category =
            categoryRepository.findByIdOrNull(categoryId) ?: throw ModelNotFoundException("category", categoryId)
        category.info = req.info
        return CategoryResponse.from(category)

    }

    override fun deleteCategory(categoryId: Long) {
        val category =
            categoryRepository.findByIdOrNull(categoryId) ?: throw ModelNotFoundException("category", categoryId)
        categoryRepository.delete(category)
    }
}