package com.example.days.domain.admin.service

import com.example.days.domain.admin.dto.request.LoginAdminRequest
import com.example.days.domain.admin.dto.request.SignUpAdminRequest
import com.example.days.domain.admin.dto.response.AdminResponse
import com.example.days.domain.admin.dto.response.LoginAdminResponse
import com.example.days.domain.admin.model.Admin
import com.example.days.domain.admin.model.checkingEmailAndNicknameExists
import com.example.days.domain.admin.repository.AdminRepository
import com.example.days.domain.messages.dto.request.CreateMessageRequest
import com.example.days.domain.messages.dto.response.AdminMessagesSendResponse
import com.example.days.domain.messages.model.AdminMessagesEntity
import com.example.days.domain.messages.repository.AdminMessagesRepository
import com.example.days.domain.report.dto.response.UserReportResponse
import com.example.days.domain.report.repository.ReportRepository
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
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdminServiceImpl(
    private val adminRepository: AdminRepository,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val reportRepository: ReportRepository,
    private val adminMessagesRepository: AdminMessagesRepository,
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


    override fun getAllUser(pageable: Pageable, status: String?): Page<UserResponse> {
        val userStatus = when (status) {
            "WARNING" -> Status.WARNING
            "BAN" -> Status.BAN
            "ACTIVE" -> Status.ACTIVE
            "WITHDRAW" -> Status.WITHDRAW
            null -> null
            else -> throw IllegalArgumentException("The status is invalid");
        }
        return adminRepository.findByPageableUserAndStatus(pageable, userStatus).map { UserResponse.from(it) }
    }

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

    override fun getReportUser(pageable: Pageable, nickname: String): Page<UserReportResponse> {
        return reportRepository.findByPageableAndNickname(pageable, nickname).map { UserReportResponse.from(it) }
    }

    override fun toUserCreateMessage(req: CreateMessageRequest, userId: Long): AdminMessagesSendResponse {
        val receiverNickname = userRepository.findByNickname(req.receiverNickname) ?: TODO()
        val admin = adminRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("Admin", userId)
        // 어드민 가능하게 해야함.
        val adminMessages = adminMessagesRepository.save(
            AdminMessagesEntity(
                title = req.title,
                content = req.content,
                receiver = receiverNickname,
                admin = admin,
                deletedByReceiver = false
            )
        )
        return AdminMessagesSendResponse.from(adminMessages)
    }

    override fun readAllMessagesOnlyAdmin(pageable: Pageable, userId: Long): Page<AdminMessagesSendResponse> {
        return adminMessagesRepository.findAll(pageable).map { AdminMessagesSendResponse.from(it) }
    }
}