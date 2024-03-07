package com.example.days.domain.user.service

import com.example.days.domain.user.dto.request.EmailRequest
import com.example.days.domain.user.dto.request.LoginRequest
import com.example.days.domain.user.dto.request.SignUpRequest
import com.example.days.domain.user.dto.response.EmailResponse
import com.example.days.domain.user.dto.response.LoginResponse
import com.example.days.domain.user.dto.response.SignUpResponse
import com.example.days.domain.user.model.Status
import com.example.days.domain.user.model.User
import com.example.days.domain.user.model.UserRole
import com.example.days.domain.user.repository.QueryDslUserRepository
import com.example.days.domain.user.repository.UserRepository
import com.example.days.global.infra.mail.MailUtility
import com.example.days.global.infra.regex.RegexFunc
import com.example.days.global.infra.security.jwt.JwtPlugin
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    val userRepository: UserRepository,
    val queryDslUserRepository: QueryDslUserRepository,
    val mailUtility: MailUtility,
    private val passwordEncoder: PasswordEncoder,
    private val jwtPlugin: JwtPlugin,
    private val regexFunc: RegexFunc
) : UserService {

    override fun login(request: LoginRequest): LoginResponse {
        val user = userRepository.findUserByEmail(regexFunc.regexUserEmail(request.email))
            ?.takeIf { passwordEncoder.matches(regexFunc.regexPassword(request.password), it.password) }
            ?: throw IllegalArgumentException("이메일 또는 패스워드가 일치하지 않습니다.")

        if (user.status == Status.BAN) throw IllegalArgumentException("해당 유저는 활동정지 상태입니다.")

        return LoginResponse(
            accessToken = jwtPlugin.generateAccessToken(
                id = user.id!!,
                status = user.status,
                role = user.role
            ), nickname = user.nickname
             , message = "로그인이 완료되었습니다."
        )
    }

    override fun signUp(request: SignUpRequest): SignUpResponse {
        if (userRepository.existsByEmail(regexFunc.regexUserEmail(request.email)))
            throw IllegalArgumentException("이미 동일한 이메일이 존재합니다.")

        val pass =
            if (request.password == request.newPassword) passwordEncoder.encode(regexFunc.regexPassword(request.password))
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
        val mail = mailUtility.passwordChangeEMail(request.email)
        val user = userRepository.findUserByEmail(request.email)

        if (user != null) {
            user.email = request.email
            user.password = mail
            userRepository.save(user)
        } else {
            throw IllegalArgumentException("해당 이메일을 사용하는 회원이 없습니다.")
        }
    }



}