package com.example.days.domain.admin.controller

import com.example.days.domain.admin.dto.request.LoginAdminRequest
import com.example.days.domain.admin.dto.request.SignUpAdminRequest
import com.example.days.domain.admin.dto.request.UserBanRequest
import com.example.days.domain.admin.dto.response.AdminResponse
import com.example.days.domain.admin.dto.response.LoginAdminResponse
import com.example.days.domain.admin.service.AdminService
import com.example.days.domain.user.dto.response.UserResponse
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/admins")
class AdminController(
    private val adminService: AdminService
) {

    @Operation(summary = "어드민 회원가입")
    @PostMapping("/signup")
    fun adminSignup(
        @Valid @RequestBody req: SignUpAdminRequest
    ): ResponseEntity<AdminResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.adminSignup(req))
    }

    @Operation(summary = "어드민 로그인")
    @PostMapping("/login")
    fun adminLogin(@RequestBody @Valid req: LoginAdminRequest): ResponseEntity<LoginAdminResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.adminLogin(req))
    }

    @Operation(summary = "유저 조회")
    @GetMapping("/users")
    fun getUsersByAdmin(
        @PageableDefault(size = 10, sort = ["id"]) pageable: Pageable
    ): ResponseEntity<Page<UserResponse>> {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.getAllUser(pageable))
    }

    @Operation(summary = "유저 밴 처리")
    @PutMapping("/users/{userId}")
    fun userBanByAdmin(
        @PathVariable userId: Long,
        @RequestBody req: UserBanRequest
    ): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.userBanByAdmin(userId, req))
    }

    //ㅇㅅㅇ 유저 삭제하는 부분 아직 스케줄러는 미완성
    @Operation(summary = "유저 삭제 처리")
    @DeleteMapping("/users/{userId}")
    fun userDeleteByAdmin(
        @PathVariable userId: Long,
    ): ResponseEntity<Unit> {
        adminService.userDeleteByAdmin(userId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

    @Operation(summary = "어드민 밴 처리")
    @DeleteMapping("/{adminId}")
    fun adminBanByAdmin(
        @PathVariable adminId: Long
    ): ResponseEntity<Unit> {
        adminService.adminBanByAdmin(adminId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

}