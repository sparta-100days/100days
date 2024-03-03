package com.example.days.domain.user.service

import com.example.days.domain.user.dto.request.LoginRequest
import com.example.days.domain.user.dto.request.SignUpRequest
import com.example.days.domain.user.dto.response.LoginResponse
import com.example.days.domain.user.dto.response.SignUpResponse
import com.example.days.domain.user.model.User
import com.example.days.domain.user.model.UserRole
import com.example.days.domain.user.model.UserStatus
import com.example.days.domain.user.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
): UserService {

    override fun login(request: LoginRequest): LoginResponse {
        val user = userRepository.findUserByEmail(request.email)
            ?.takeIf { passwordEncoder.matches(request.password, it.password) }
            ?: throw IllegalArgumentException("이메일 또는 패스워드가 일치하지 않습니다.")

        return LoginResponse.from(user)
    }

    @Transactional
    override fun signUp(request: SignUpRequest): SignUpResponse {
        if (userRepository.existsByEmail(request.email)) throw IllegalArgumentException("이미 동일한 이메일이 존재합니다.")

        val pass = if (request.password == request.newPassword) passwordEncoder.encode(request.password)
            else throw IllegalArgumentException("비밀번호 확인이 일치하지 않습니다.")

        return User(
            email = request.email,
            nickName = request.nickName,
            password = pass,
            birth = request.birth,
            isDelete = false,
            status = UserStatus.ACTIVE,
            role = UserRole.USER
        ).let {
            userRepository.save(it)
        }.let { SignUpResponse.from(it) }
    }


}