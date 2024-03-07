package com.example.days.domain.admin.controller

import com.example.days.domain.admin.dto.request.SignUpAdminRequest
import com.example.days.domain.admin.dto.request.UserBanRequest
import com.example.days.domain.admin.dto.response.AdminResponse
import com.example.days.domain.admin.service.AdminService
import com.example.days.domain.user.dto.response.UserResponse
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admins")
class AdminController(
    private val adminService: AdminService
) {

    @PostMapping("/signup")
    fun adminSignup(
        @Valid @RequestBody req: SignUpAdminRequest
    ): ResponseEntity<AdminResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.adminSignup(req))
    }

    //ㅇㅅㅇ userResponse 부분으로 바꿔야함
    @GetMapping("/users")
    fun getUsersByAdmin(
        @PageableDefault(size = 10, sort = ["id"]) pageable: Pageable
    ): ResponseEntity<Page<UserResponse>> {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.getAllUser(pageable))
    }

    @PutMapping("/users/{userId}")
    fun userBanByAdmin(
        @PathVariable userId: Long,
        @RequestBody req: UserBanRequest
    ): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.userBanByAdmin(userId, req))
    }

    //ㅇㅅㅇ 유저 삭제하는 부분 아직 로직은 미완성
    @DeleteMapping("/users/{userId}")
    fun userDeleteByAdmin(
        @PathVariable userId: Long,
    ): ResponseEntity<Unit> {
        adminService.userDeleteByAdmin(userId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

    @DeleteMapping("/{adminId}")
    fun adminBanByAdmin(
        @PathVariable adminId: Long
    ): ResponseEntity<Unit> {
        adminService.adminBanByAdmin(adminId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

}