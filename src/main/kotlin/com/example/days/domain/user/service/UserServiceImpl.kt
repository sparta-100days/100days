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
            ?.takeIf { encoder.matches(regexFunc.regexPassword(request.password), it.password) }
            ?: throw IllegalArgumentException("이메일 또는 패스워드가 일치하지 않습니다.")

        if (user.status == Status.BAN) throw IllegalArgumentException("해당 유저는 활동정지 상태입니다.")
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
            throw IllegalArgumentException("이미 동일한 이메일이 존재합니다.")

        if (userRepository.existsByNickname(request.nickname))
            throw IllegalArgumentException("이름은 중복될 수 없습니다.")

        val pass =
            if (request.password == request.newPassword) encoder.encode(regexFunc.regexPassword(request.password))
            else throw IllegalArgumentException("비밀번호 확인이 일치하지 않습니다.")

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
            throw IllegalArgumentException("해당 이메일을 사용하는 회원이 없습니다.")
        }
    }

    override fun getInfo(userId: UserPrincipal): ModifyInfoResponse {
        val user = userRepository.findByIdOrNull(userId.id) ?: throw IllegalArgumentException("회원정보가 없습니다.")
        return user.let { ModifyInfoResponse.from(it) }
    }

    @Transactional
    override fun modifyInfo(userId: UserPrincipal, request: ModifyInfoRequest): ModifyInfoResponse {
        val user = userRepository.findByIdOrNull(userId.id) ?: throw IllegalArgumentException("회원정보가 없습니다.")

        if (encoder.matches(regexFunc.regexPassword(request.password), user.password)) {

            user.nickname = request.nickname
            user.birth = request.birth

            userRepository.save(user)
        } else {
            throw IllegalArgumentException("비밀번호가 일치하지 않습니다.")
        }

        return ModifyInfoResponse(user.email, user.nickname, user.birth)
    }

    override fun withdraw(userId: UserPrincipal, request: UserPasswordRequest) {
        val user = userRepository.findByIdOrNull(userId.id) ?: throw IllegalArgumentException("회원정보가 없습니다.")
        if (encoder.matches(regexFunc.regexPassword(request.password), user.password)) {
            user.isDelete = true
            user.status = Status.WITHDRAW
            userRepository.save(user)
        } else {
            throw IllegalArgumentException("비밀번호가 일치하지 않습니다.")
        }
    }

    override fun passwordChange(userId: UserPrincipal, request: UserPasswordRequest) {
        val user = userRepository.findByIdOrNull(userId.id) ?: throw IllegalArgumentException("회원정보가 없습니다.")

        if (encoder.matches(request.password, user.password))
            throw IllegalArgumentException("이전에 사용한 비밀번호와 같은 비밀번호는 사용할 수 없습니다.")

        if (request.password == request.newPassword) {
            user.password = encoder.encode(regexFunc.regexPassword(request.newPassword))
            userRepository.save(user)
        } else {
            throw IllegalArgumentException("비밀번호 확인이 일치하지 않습니다.")
        }
    }

    @Scheduled(cron = "0 0 12 * * ?")
    fun userDeletedAuto() {
        val nowTime = LocalDateTime.now()
        val userDeleteAuto = nowTime.minusDays(7)
        userRepository.deleteUsersByStatusAndupdatedAtIsLessThanEqualBatch(Status.WITHDRAW, userDeleteAuto)
    }

}