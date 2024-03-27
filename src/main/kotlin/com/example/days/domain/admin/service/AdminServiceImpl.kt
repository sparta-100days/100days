package com.example.days.domain.admin.service

import com.example.days.domain.admin.dto.request.LoginAdminRequest
import com.example.days.domain.admin.dto.request.SignUpAdminRequest
import com.example.days.domain.admin.dto.request.UserBanRequest
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
import com.example.days.global.common.exception.common.AlreadyBANException
import com.example.days.global.common.exception.common.ModelNotFoundException
import com.example.days.global.common.exception.user.MismatchPasswordException
import com.example.days.global.common.exception.user.NoSearchUserByEmailException
import com.example.days.global.common.exception.user.UserNotFoundException
import com.example.days.global.common.exception.user.UserSuspendedException
import com.example.days.global.infra.regex.RegexFunc
import com.example.days.global.infra.security.jwt.JwtPlugin
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

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
            ?: throw NoSearchUserByEmailException(req.email)
            if(!passwordEncoder.matches(regexFunc.regexPassword(req.password), admin.password)){
                throw MismatchPasswordException()
            }

        if(admin.status == Status.BAN) throw throw UserSuspendedException()

        return LoginAdminResponse(
            accessToken = jwtPlugin.accessToken(
                id = admin.id!!,
                email = admin.email,
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
            else -> throw IllegalArgumentException("The status is invalid")
        }
        return adminRepository.findByPageableUserAndStatus(pageable, userStatus).map { UserResponse.from(it) }
    }

    @Transactional
    override fun userBanByAdmin(userId: Long, req: UserBanRequest): String {
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User", userId)
        if(user.status == Status.BAN){
            throw AlreadyBANException("이미 밴 처리된 계정입니다.")
        }
        user.period= req.period
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

    override fun getReportUser(pageable: Pageable, reportedUserNickname: String): Page<UserReportResponse> {
        return reportRepository.findByPageableAndReportedUserNickname(pageable, reportedUserNickname).map { UserReportResponse.from(it) }
    }

    override fun toUserCreateMessage(req: CreateMessageRequest, userId: Long): AdminMessagesSendResponse {
        val receiverNickname = userRepository.findByNickname(req.receiverNickname) ?: throw UserNotFoundException()
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

    @Transactional
    @Scheduled(fixedRate = 86400000)//하루로 지정해둠 어차피 LOCALDATE로 했으니까?
    fun banPeriod(){
        val nowDate = LocalDate.now()
        userRepository.checkBanPeriod(Status.ACTIVE, nowDate)
    }
}