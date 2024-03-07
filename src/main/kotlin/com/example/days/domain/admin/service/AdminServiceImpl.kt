package com.example.days.domain.admin.service

import com.example.days.domain.admin.dto.request.SignUpAdminRequest
import com.example.days.domain.admin.dto.response.AdminResponse
import com.example.days.domain.admin.model.Admin
import com.example.days.domain.admin.repository.AdminRepository
import com.example.days.domain.user.dto.response.UserResponse
import com.example.days.domain.user.model.Status
import com.example.days.domain.user.repository.UserRepository
import com.example.days.global.common.exception.ModelNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdminServiceImpl(
    private val adminRepository: AdminRepository,
    private val userRepository: UserRepository
) : AdminService {
    override fun adminSignup(req: SignUpAdminRequest): AdminResponse {
        return adminRepository.save(
            Admin(
                nickname = req.nickname,
                isDelete = false,
                email = req.email,
                password = req.password
            )
        ).let { AdminResponse.from(it) }
    }

    override fun getAllUser(): List<UserResponse> {
        return userRepository.findAll().map { UserResponse.from(it) }
    }

    //이건 밴처리만
    //또 탈퇴처리하는건 후에 하자
    @Transactional
    override fun userBanByAdmin(userId: Long) {
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User",userId)
        user.status = Status.BAN
        userRepository.save(user)

    }

    @Transactional
    override fun adminBanByAdmin(adminId: Long) {
        val admin = adminRepository.findByIdOrNull(adminId) ?: throw ModelNotFoundException("Admin",adminId)
        admin.adminBanByAdmin()
        adminRepository.save(admin)
    }


}