package com.example.days.domain.admin.controller

import com.example.days.domain.admin.dto.request.SignUpAdminRequest
import com.example.days.domain.admin.dto.response.AdminResponse
import com.example.days.domain.admin.service.AdminService
import com.example.days.domain.user.dto.response.UserResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admin")
class AdminController(
    private val adminService: AdminService
) {

    @PostMapping("/signup")
    fun adminSignup(
        @Valid @RequestBody req: SignUpAdminRequest
    ): ResponseEntity<AdminResponse>{
        return ResponseEntity.status(HttpStatus.OK).body(adminService.adminSignup(req))
    }

    //ㅇㅅㅇ userResponse 부분으로 바꿔야함
    @GetMapping("/users")
    fun getUsersByAdmin(): ResponseEntity<List<UserResponse>>{
        return ResponseEntity.status(HttpStatus.OK).body(adminService.getAllUser())
    }

    @PutMapping("/users/{userId}")
    fun userBanByAdmin(
        @PathVariable userId: Long
    ): ResponseEntity<Unit> {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.userBanByAdmin(userId))
    }

    @DeleteMapping("/{adminId}")
    fun adminBanByAdmin(
        @PathVariable adminId: Long
    ): ResponseEntity<Unit>{
        adminService.adminBanByAdmin(adminId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

}