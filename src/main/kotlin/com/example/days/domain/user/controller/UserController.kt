package com.example.days.domain.user.controller

import com.example.days.domain.user.dto.request.LoginRequest
import com.example.days.domain.user.dto.request.SignUpRequest
import com.example.days.domain.user.dto.response.EmailResponse
import com.example.days.domain.user.dto.response.LoginResponse
import com.example.days.domain.user.dto.response.SignUpResponse
import com.example.days.domain.user.service.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController (
    val userService: UserService
){

    @GetMapping("/searchEmail")
    fun searchUserEmail(@RequestParam(value = "nickname") nickname: String): ResponseEntity<List<EmailResponse>> {
        return ResponseEntity.status(HttpStatus.OK).body(userService.searchUserEmail(nickname))
    }

    @PostMapping("/login")
    fun userLogin(@RequestBody @Valid request: LoginRequest): ResponseEntity<LoginResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(userService.login(request))
    }

    @PostMapping("/signup")
    fun signUp(@RequestBody @Valid request: SignUpRequest): ResponseEntity<SignUpResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.signUp(request))
    }

}