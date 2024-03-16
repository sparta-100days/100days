package com.example.days.domain.admin.controller

import com.example.days.domain.admin.dto.request.LoginAdminRequest
import com.example.days.domain.admin.dto.request.SignUpAdminRequest
import com.example.days.domain.admin.dto.request.UserBanRequest
import com.example.days.domain.admin.dto.response.AdminResponse
import com.example.days.domain.admin.dto.response.LoginAdminResponse
import com.example.days.domain.admin.service.AdminService
import com.example.days.domain.messages.dto.request.CreateMessageRequest
import com.example.days.domain.messages.dto.response.AdminMessagesSendResponse
import com.example.days.domain.report.dto.response.UserReportResponse
import com.example.days.domain.user.dto.response.UserResponse
import com.example.days.global.infra.security.UserPrincipal
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
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
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    fun getUsersByAdmin(
        @PageableDefault(size = 10, sort = ["id"]) pageable: Pageable,
        @RequestParam(value = "status", required = false) status: String?
    ): ResponseEntity<Page<UserResponse>> {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.getAllUser(pageable, status))
    }

    @Operation(summary = "유저 밴 처리")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/users/{userId}")
    fun userBanByAdmin(
        @PathVariable userId: Long,
        @RequestBody req: UserBanRequest
    ): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.userBanByAdmin(userId, req))
    }


    @Operation(summary = "유저 삭제 처리")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/users/{userId}")
    fun userDeleteByAdmin(
        @PathVariable userId: Long,
    ): ResponseEntity<Unit> {
        adminService.userDeleteByAdmin(userId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

    @Operation(summary = "어드민 밴 처리")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{adminId}")
    fun adminBanByAdmin(
        @PathVariable adminId: Long
    ): ResponseEntity<Unit> {
        adminService.adminBanByAdmin(adminId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

    @Operation(summary = "신고 당한 유저 조회")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/reports/users")
    fun getReportUser(
        @PageableDefault(size = 10, sort = ["nickname"]) pageable: Pageable,
        @RequestParam reportedUserNickname: String,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<Page<UserReportResponse>> {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.getReportUser(pageable, reportedUserNickname))
    }

    @Operation(summary = "TO 유저 FROM 어드민 쪽지 생성")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/messages")
    fun toUserCreateMessage(
        @RequestBody req: CreateMessageRequest,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<AdminMessagesSendResponse> {
        val userId = userPrincipal.id
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.toUserCreateMessage(req, userId))
    }

    @Operation(summary = "어드민 쪽지 전체 조회 Only 어드민만")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/messages/read/all")
    fun readAllMessagesOnlyAdmin(
        @PageableDefault(size = 10, sort = ["sentAt"]) pageable: Pageable,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<Page<AdminMessagesSendResponse>> {
        val userId = userPrincipal.id
        return ResponseEntity.status(HttpStatus.OK).body(adminService.readAllMessagesOnlyAdmin(pageable, userId))
    }

}