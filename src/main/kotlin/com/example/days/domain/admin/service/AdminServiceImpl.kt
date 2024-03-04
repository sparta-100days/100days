package com.example.days.domain.admin.service

import com.example.days.domain.admin.dto.request.SignUpAdminRequest
import com.example.days.domain.admin.dto.response.AdminResponse
import com.example.days.domain.admin.exception.ModelNotFoundException
import com.example.days.domain.admin.model.Admin
import com.example.days.domain.admin.repository.AdminRepository
import com.example.days.domain.user.dto.response.UserResponse
import com.example.days.domain.user.model.UserStatus
import com.example.days.domain.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.scheduling.annotation.Scheduled
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
    override fun userBanByAdmin(userId: Long) {
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User",userId)
//        user.userBanByAdmin()
//        if (user.isDelete){
//            user.status = UserStatus.BAN
//        }
        userRepository.save(user)

    }

    @Transactional
    override fun adminBanByAdmin(adminId: Long) {
        val admin = adminRepository.findByIdOrNull(adminId) ?: throw ModelNotFoundException("Admin",adminId)
        admin.adminBanByAdmin()
        adminRepository.save(admin)
    }


}