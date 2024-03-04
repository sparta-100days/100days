package com.example.days.domain.admin.service

import com.example.days.domain.admin.dto.request.SignUpAdminRequest
import com.example.days.domain.admin.dto.request.UserBanRequest
import com.example.days.domain.admin.dto.response.AdminResponse
import com.example.days.domain.admin.model.Admin
import com.example.days.domain.admin.repository.AdminRepository
import com.example.days.domain.user.dto.response.UserResponse
import com.example.days.domain.user.model.UserStatus
import com.example.days.domain.user.repository.UserRepository
import com.example.days.global.common.exception.ModelNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdminServiceImpl(
    private val adminRepository: AdminRepository,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : AdminService {
    override fun adminSignup(req: SignUpAdminRequest): AdminResponse {
        return adminRepository.save(
            Admin(
                nickname = req.nickname,
                isDelete = false,
                email = req.email,
                password = passwordEncoder.encode(req.password)
            )
        ).let { AdminResponse.from(it) }
    }

    override fun getAllUser(): List<UserResponse> {
        return userRepository.findAll().map { UserResponse.from(it) }
    }

    //이건 밴처리만
    //또 탈퇴처리하는건 후에 하자
    @Transactional
    override fun userBanByAdmin(userId: Long, req: UserBanRequest): String {
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User",userId)
        if (req.status != UserStatus.BAN) {
            throw HttpMessageNotReadableException("BAN만 가능합니다. BAN을 입력해주세요")
        } else {
            user.status = req.status
            userRepository.save(user)
        }
        return "밴처리 되었습니다!!"
    }

    @Transactional
    override fun userDeleteByAdmin(userId: Long) {
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User",userId)
        TODO()
    }

    @Transactional
    override fun adminBanByAdmin(adminId: Long) {
        val admin = adminRepository.findByIdOrNull(adminId) ?: throw ModelNotFoundException("Admin",adminId)
        admin.adminBanByAdmin()
        adminRepository.save(admin)
    }


}