package com.example.days.domain.user.controller

import com.example.days.domain.mail.dto.request.EmailRequest
import com.example.days.domain.mail.dto.response.EmailResponse
import com.example.days.domain.user.dto.request.*
import com.example.days.domain.user.dto.response.*
import com.example.days.domain.user.service.UserService
import com.example.days.global.infra.security.UserPrincipal
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.nio.charset.StandardCharsets

@RestController
@RequestMapping("/api/users")
class UserController(
    val userService: UserService
) {

    @Operation(summary = "이메일 찾기")
    @GetMapping("/searchEmail")
    fun searchUserEmail(@RequestParam(value = "nickname") nickname: String): ResponseEntity<List<EmailResponse>> {
        return ResponseEntity.status(HttpStatus.OK).body(userService.searchUserEmail(nickname))
    }

    @Operation(summary = "비밀번호 분실시 재발급(메일보내기)")
    @PatchMapping("/searchPass")
    fun changeUserPassword(@RequestBody request: EmailRequest): ResponseEntity<Unit> {
        userService.changeUserPassword(request)
        return ResponseEntity.status(HttpStatus.OK).build()
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    fun userLogin(@RequestBody @Valid request: LoginRequest): ResponseEntity<LoginResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(userService.login(request))
    }

    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    fun signUp(@RequestBody @Valid request: SignUpRequest): ResponseEntity<SignUpResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.signUp(request))
    }

    @Operation(summary = "회원정보 보기(로그인 회원)")
    @GetMapping("/myinfo")
    fun getInfo(
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<ModifyInfoResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getInfo(userPrincipal))
    }

    @Operation(summary = "회원정보 수정(로그인 회원)")
    @PutMapping()
    fun modifyInfo(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @RequestBody @Valid request: ModifyInfoRequest
    ): ResponseEntity<ModifyInfoResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.modifyInfo(userPrincipal, request))
    }

    @Operation(summary = "회원탈퇴")
    @DeleteMapping()
    fun withdraw(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @RequestBody @Valid request: UserPasswordRequest
    ): ResponseEntity<Unit> {
        userService.withdraw(userPrincipal, request)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

    @Operation(summary = "비밀번호 변경")
    @PatchMapping("/passwordchange")
    fun passwordChange(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @RequestBody @Valid request: UserPasswordRequest
    ): ResponseEntity<Unit> {
        return ResponseEntity.status(HttpStatus.OK).body(userService.passwordChange(userPrincipal, request))
    }

    @Operation(summary = "유저 검색(쪽지 등)")
    @GetMapping("/searchAccountId")
    fun searchUserAccountId(@RequestParam(value = "accountId") accountId: String): ResponseEntity<List<AccountSearchResponse>> {
        return ResponseEntity.status(HttpStatus.OK).body(userService.searchUserAccountId(accountId))
    }

    // 이게 맞는지 잘 모르겠다... 이렇게 만들어 두고 다른 방법을 찾아봐야겠다.
    @Operation(summary = "로그아웃(임시)")
    @PostMapping("/logout")
    fun logout(@AuthenticationPrincipal userPrincipal: UserPrincipal): ResponseEntity<LogoutResponse> {
        val response = userService.logout(userPrincipal) // 서비스에서 유저 정보를 받아오고
        val headers = HttpHeaders()
        headers.set(HttpHeaders.AUTHORIZATION,
            "Bearer $response".toByteArray(StandardCharsets.ISO_8859_1)
                .toString(StandardCharsets.ISO_8859_1)
        ) // 여기서 헤더에 토큰이 추가가 되는 것 같다... 특문이나 한글 등이 포함되어 있지 않게 한번 변형함
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(response) // 헤더 부분 추가
    }
}