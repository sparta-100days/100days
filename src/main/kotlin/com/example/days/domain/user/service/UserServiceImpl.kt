package com.example.days.domain.user.service

import com.example.days.domain.mail.dto.request.EmailRequest
import com.example.days.domain.user.dto.request.*
import com.example.days.domain.mail.dto.response.EmailResponse
import com.example.days.domain.user.dto.response.LoginResponse
import com.example.days.domain.user.dto.response.ModifyInfoResponse
import com.example.days.domain.user.dto.response.SignUpResponse
import com.example.days.domain.user.model.Status
import com.example.days.domain.user.model.User
import com.example.days.domain.user.model.UserRole
import com.example.days.domain.user.repository.QueryDslUserRepository
import com.example.days.domain.user.repository.UserRepository
import com.example.days.global.common.exception.common.ModelNotFoundException
import com.example.days.global.common.exception.user.*
import com.example.days.global.infra.mail.MailUtility
import com.example.days.global.infra.regex.RegexFunc
import com.example.days.global.infra.security.UserPrincipal
import com.example.days.global.infra.security.jwt.JwtPlugin
import com.example.days.global.support.MailType
import org.springframework.data.repository.findByIdOrNull
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val queryDslUserRepository: QueryDslUserRepository,
    private val mailUtility: MailUtility,
    private val encoder: PasswordEncoder,
    private val jwtPlugin: JwtPlugin,
    private val regexFunc: RegexFunc
) : UserService {

    override fun login(request: LoginRequest): LoginResponse {
        val user = userRepository.findUserByEmail(regexFunc.regexUserEmail(request.email))
            ?: throw NoSearchUserByEmailException(request.email)
        if(!encoder.matches(regexFunc.regexPassword(request.password), user.password)) throw MismatchPasswordException()

        if (user.status == Status.BAN) throw UserSuspendedException()
        if (user.status == Status.WITHDRAW) {
            user.isDelete = false
            user.status = Status.ACTIVE
            userRepository.save(user)
        }

        // accessToken
        val accessToken = jwtPlugin.accessToken(
            id = user.id!!,
            email = user.email,
            role = user.role
        )

        return LoginResponse(
            accessToken,
            nickname = user.nickname,
            message = "로그인이 완료되었습니다."
        )
    }

    override fun signUp(request: SignUpRequest): SignUpResponse {
        if (userRepository.existsByEmail(regexFunc.regexUserEmail(request.email)))
            throw DuplicateEmailException(request.email)

        if (userRepository.existsByNickname(request.nickname))
            throw DuplicateNicknameException(request.nickname)

        val pass =
            if (request.password == request.newPassword) encoder.encode(regexFunc.regexPassword(request.password))
            else throw MismatchPasswordException()

        return User(
            email = regexFunc.regexUserEmail(request.email),
            nickname = request.nickname,
            password = pass,
            birth = request.birth,
            isDelete = false,
            status = Status.ACTIVE,
            role = UserRole.USER
        ).let {
            userRepository.save(it)
        }.let { SignUpResponse.from(it) }
    }

    override fun searchUserEmail(nickname: String): List<EmailResponse> {
        return queryDslUserRepository.searchUserByNickname(nickname).map { EmailResponse.from(it) }
    }

    @Transactional
    override fun changeUserPassword(request: EmailRequest) {
        val user = userRepository.findUserByEmail(request.email)

        if (user != null) {
            val mail = mailUtility.emailSender(request.email, MailType.CHANGEPASSWORD)
            user.email = request.email
            user.password = mail
            userRepository.save(user)
        } else {
            throw NoSearchUserByEmailException(request.email)
        }
    }

    override fun getInfo(userId: UserPrincipal): ModifyInfoResponse {
        val user = userRepository.findByIdOrNull(userId.id) ?: throw ModelNotFoundException("User", userId.id)
        return user.let { ModifyInfoResponse.from(it) }
    }

    @Transactional
    override fun modifyInfo(userId: UserPrincipal, request: ModifyInfoRequest): ModifyInfoResponse {
        val user = userRepository.findByIdOrNull(userId.id) ?: throw ModelNotFoundException("user", userId.id)

        if (encoder.matches(regexFunc.regexPassword(request.password), user.password)) {

            user.nickname = request.nickname
            user.birth = request.birth

            userRepository.save(user)
        } else {
            throw MismatchPasswordException()
        }

        return ModifyInfoResponse(user.email, user.nickname, user.birth)
    }

    override fun withdraw(userId: UserPrincipal, request: UserPasswordRequest) {
        val user = userRepository.findByIdOrNull(userId.id) ?: throw ModelNotFoundException("user", userId.id)
        if (encoder.matches(regexFunc.regexPassword(request.password), user.password)) {
            user.isDelete = true
            user.status = Status.WITHDRAW
            userRepository.save(user)
        } else {
            throw MismatchPasswordException()
        }
    }

    override fun passwordChange(userId: UserPrincipal, request: UserPasswordRequest) {
        val user = userRepository.findByIdOrNull(userId.id) ?: throw ModelNotFoundException("user", userId.id)

        if (encoder.matches(request.password, user.password))
            throw InvalidPasswordError()

        if (request.password == request.newPassword) {
            user.password = encoder.encode(regexFunc.regexPassword(request.newPassword))
            userRepository.save(user)
        } else {
            throw MismatchPasswordException()
        }
    }

    @Scheduled(cron = "0 0 12 * * ?")
    fun userDeletedAuto() {
        val nowTime = LocalDateTime.now()
        val userDeleteAuto = nowTime.minusDays(7)
        userRepository.deleteUsersByStatusAndUpdatedAtIsLessThanEqualBatch(Status.WITHDRAW, userDeleteAuto)
    }

}
