package com.example.days.domain.admin.service

import com.example.days.domain.admin.dto.request.SignUpAdminRequest
import com.example.days.domain.admin.dto.response.AdminResponse
import com.example.days.domain.admin.exception.ModelNotFoundException
import com.example.days.domain.admin.model.Admin
import com.example.days.domain.admin.repository.AdminRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdminServiceImpl(
    private val adminRepository: AdminRepository
) : AdminService {
    override fun adminSignup(req: SignUpAdminRequest): AdminResponse {
        return adminRepository.save(
            Admin(
                nickname = req.nickname,
                isDeleted = false,
                email = req.email,
                password = req.password
            )
        ).let { AdminResponse.from(it) }
    }

    override fun getAllUser(): List<AdminResponse> {
        TODO("Not yet implemented")
    }

    override fun userBanByAdmin() {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun adminBanByAdmin(adminId: Long) {
        val admin = adminRepository.findByIdOrNull(adminId) ?: throw ModelNotFoundException("Admin",adminId)
        admin.adminBanByAdmin()
        adminRepository.save(admin)
    }
}