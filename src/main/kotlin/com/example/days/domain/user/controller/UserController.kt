package com.example.days.domain.user.controller

import com.example.days.domain.user.dto.request.*
import com.example.days.domain.user.dto.response.*
import com.example.days.domain.user.service.UserService
import com.example.days.global.infra.security.UserPrincipal
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(
    val userService: UserService
) {

    @GetMapping("/searchEmail")
    fun searchUserEmail(@RequestParam(value = "nickname") nickname: String): ResponseEntity<List<EmailResponse>> {
        return ResponseEntity.status(HttpStatus.OK).body(userService.searchUserEmail(nickname))
    }

    @PatchMapping("/searchPass")
    fun changeUserPassword(@RequestBody request: EmailRequest): ResponseEntity<Unit> {
        userService.changeUserPassword(request)
        return ResponseEntity.status(HttpStatus.OK).build()
    }

    @PostMapping("/login")
    fun userLogin(@RequestBody @Valid request: LoginRequest): ResponseEntity<LoginResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(userService.login(request))
    }

    @PostMapping("/signup")
    fun signUp(@RequestBody @Valid request: SignUpRequest): ResponseEntity<SignUpResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.signUp(request))
    }

    @PutMapping()
    fun modifyInfo(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @RequestBody @Valid request: ModifyInfoRequest
    ): ResponseEntity<ModifyInfoResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.modifyInfo(userPrincipal, request))
    }

    @DeleteMapping()
    fun withdraw(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @RequestBody @Valid request: UserPasswordRequest
    ): ResponseEntity<Unit> {
        userService.withdraw(userPrincipal, request)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

    @PatchMapping("/passwordchange")
    fun passwordChange(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @RequestBody @Valid request: UserPasswordRequest
    ): ResponseEntity<Unit> {
        return ResponseEntity.status(HttpStatus.OK).body(userService.passwordChange(userPrincipal, request))
    }
}