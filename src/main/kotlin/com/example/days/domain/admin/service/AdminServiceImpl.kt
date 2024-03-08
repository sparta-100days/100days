package com.example.days.domain.admin.service

import com.example.days.domain.admin.dto.request.LoginAdminRequest
import com.example.days.domain.admin.dto.request.SignUpAdminRequest
import com.example.days.domain.admin.dto.request.UserBanRequest
import com.example.days.domain.admin.dto.response.AdminResponse
import com.example.days.domain.admin.dto.response.LoginAdminResponse
import com.example.days.domain.admin.model.Admin
import com.example.days.domain.admin.model.checkingEmailAndNicknameExists
import com.example.days.domain.admin.repository.AdminRepository
import com.example.days.domain.user.dto.response.UserResponse
import com.example.days.domain.user.model.Status
import com.example.days.domain.user.model.UserRole
import com.example.days.domain.user.repository.UserRepository
import com.example.days.global.common.exception.ModelNotFoundException
import com.example.days.global.infra.regex.RegexFunc
import com.example.days.global.infra.security.jwt.JwtPlugin
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class AdminServiceImpl(
    private val adminRepository: AdminRepository,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtPlugin: JwtPlugin,
    private val regexFunc: RegexFunc
) : AdminService {
    override fun adminSignup(req: SignUpAdminRequest): AdminResponse {
        checkingEmailAndNicknameExists(
            req.email,
            req.nickname,
            adminRepository
        )

        return adminRepository.save(
            Admin(
                nickname = req.nickname,
                status = Status.ACTIVE,
                email = req.email,
                password = passwordEncoder.encode(req.password),
                role = UserRole.ADMIN
            )
        ).let { AdminResponse.from(it) }
    }

    override fun adminLogin(req: LoginAdminRequest): LoginAdminResponse {
        val admin = adminRepository.findAdminByEmail(regexFunc.regexUserEmail(req.email))
            ?.takeIf { passwordEncoder.matches(regexFunc.regexPassword(req.password), it.password) }
            ?: throw IllegalArgumentException("이메일 또는 패스워드가 일치하지 않습니다.")
        if(admin.status == Status.BAN) throw IllegalArgumentException("해당 유저는 활동정지 상태입니다.")

        return LoginAdminResponse(
            accessToken = jwtPlugin.generateAccessToken(
                id = admin.id!!,
                status = admin.status,
                role = admin.role
            ), nickname = admin.nickname, message = "로그인이 완료되었습니다."
        )
    }


    override fun getAllUser(pageable: Pageable): Page<UserResponse> {
        return adminRepository.findByPageableUser(pageable).map { UserResponse.from(it) }
    }

    //이건 밴처리만
    //또 탈퇴처리하는건 후에 하자
    @Transactional
    override fun userBanByAdmin(userId: Long): String {
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User", userId)
        user.userBanByAdmin()
        userRepository.save(user)
        return "밴처리 되었습니다!!"
    }

    @Transactional
    override fun userDeleteByAdmin(userId: Long) {
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User", userId)
        user.userDeleteByAdmin()
        user.userIsDeletedByAdmin()
        userRepository.save(user)
    }

    @Transactional
    override fun adminBanByAdmin(adminId: Long) {
        val admin = adminRepository.findByIdOrNull(adminId) ?: throw ModelNotFoundException("Admin", adminId)
        admin.adminBanByAdmin()
        adminRepository.save(admin)
    }
}