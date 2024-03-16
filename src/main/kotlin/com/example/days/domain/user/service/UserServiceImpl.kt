package com.example.days.domain.user.service

import com.example.days.domain.user.dto.request.*
import com.example.days.domain.user.dto.response.EmailResponse
import com.example.days.domain.user.dto.response.LoginResponse
import com.example.days.domain.user.dto.response.ModifyInfoResponse
import com.example.days.domain.user.dto.response.SignUpResponse
import com.example.days.domain.user.model.Status
import com.example.days.domain.user.model.User
import com.example.days.domain.user.model.UserRole
import com.example.days.domain.user.repository.QueryDslUserRepository
import com.example.days.domain.user.repository.UserRepository
import com.example.days.global.common.exception.ModelNotFoundException
import com.example.days.global.common.exception.user.DuplicateEmailException
import com.example.days.global.common.exception.user.MismatchPasswordException
import com.example.days.global.common.exception.user.NoSearchUserByEmailException
import com.example.days.global.common.exception.user.UserSuspendedException
import com.example.days.global.infra.mail.MailUtility
import com.example.days.global.infra.regex.RegexFunc
import com.example.days.global.infra.security.UserPrincipal
import com.example.days.global.infra.security.jwt.JwtPlugin
import org.springframework.data.repository.findByIdOrNull
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class UserServiceImpl(
    val userRepository: UserRepository,
    val queryDslUserRepository: QueryDslUserRepository,
    val mailUtility: MailUtility,
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

        return LoginResponse(
            accessToken = jwtPlugin.generateAccessToken(
                id = user.id!!,
                status = user.status,
                role = user.role
            ), nickname = user.nickname, message = "로그인이 완료되었습니다."
        )
    }

    override fun signUp(request: SignUpRequest): SignUpResponse {
        if (userRepository.existsByEmail(regexFunc.regexUserEmail(request.email)))
            throw DuplicateEmailException(request.email)

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
            ?: throw IllegalArgumentException("해당 이메일을 사용하는 회원이 없습니다.")
        val mail = mailUtility.passwordChangeEMail(request.email)

        user.email = request.email
        user.password = mail
        userRepository.save(user)
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
        userRepository.deleteUsersByStatusAndupdatedAtIsLessThanEqualBatch(Status.WITHDRAW, userDeleteAuto)
    }

}
