package com.example.days.domain.admin.service

import com.example.days.domain.admin.dto.request.SignUpAdminRequest
import com.example.days.domain.admin.dto.response.AdminResponse
import com.example.days.domain.admin.model.Admin
import com.example.days.domain.admin.repository.AdminRepository
import com.example.days.domain.category.repository.CategoryRepository
import org.springframework.stereotype.Service

@Service
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

    override fun getAllResolution(): List<AdminResponse> {
        TODO("Not yet implemented")
    }

    override fun getAllPost(): List<AdminResponse> {
        TODO("Not yet implemented")
    }

    override fun userBanByAdmin() {
        TODO("Not yet implemented")
    }

    override fun adminBanByAdmin() {
        TODO("Not yet implemented")
    }
}