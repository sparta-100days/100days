package com.example.days.domain.user.service

import com.example.days.domain.user.dto.request.LoginRequest
import com.example.days.domain.user.dto.request.SignUpRequest
import com.example.days.domain.user.dto.response.LoginResponse
import com.example.days.domain.user.dto.response.SignUpResponse
import com.example.days.domain.user.model.User
import com.example.days.domain.user.model.UserStatus
import com.example.days.domain.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    val userRepository: UserRepository
): UserService {

    override fun login(request: LoginRequest): LoginResponse {
        val user = userRepository.findUserByEmail(request.email) ?: throw IllegalArgumentException("가입된 이메일이 아닙니다.")
        if (request.password != user.password) throw IllegalArgumentException("패스워드가 일치하지 않습니다.")
        return LoginResponse.from(user)
    }

    @Transactional
    override fun signUp(request: SignUpRequest): SignUpResponse {
        if (userRepository.existsByEmail(request.email)) throw IllegalArgumentException("이미 동일한 이메일이 존재합니다.")
        return User(
            email = request.email,
            nickName = request.nickName,
            password = request.password,
            birth = request.birth,
            isDelete = false,
            status = UserStatus.ACTIVE,
        ).let {
            userRepository.save(it)
        }.let { SignUpResponse.from(it) }
    }
}