package com.example.days.domain.report.controller

import com.example.days.domain.report.dto.request.UserReportRequest
import com.example.days.domain.report.dto.response.UserReportResponse
import com.example.days.domain.report.service.ReportService
import com.example.days.global.infra.security.UserPrincipal
import io.swagger.v3.oas.annotations.Operation
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ReportController(
    private val reportService: ReportService
) {

    @Operation(summary = "유저 신고 기능")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/reports/users")
    fun reportUser(@RequestBody req: UserReportRequest, @AuthenticationPrincipal userPrincipal: UserPrincipal): ResponseEntity<UserReportResponse>{
        val userId = userPrincipal.id
        return ResponseEntity.status(HttpStatus.CREATED).body(reportService.reportUser(req, userId))
    }

    @Operation(summary = "신고 당한 유저 조회")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/reports/users")
    fun getReportUser(
        @PageableDefault(size = 10, sort = ["nickname"]) pageable: Pageable,
        @RequestParam nickname: String,
        @AuthenticationPrincipal userPrincipal: UserPrincipal): ResponseEntity<Page<UserReportResponse>> {
        return ResponseEntity.status(HttpStatus.OK).body(reportService.getReportUser(pageable, nickname))
    }
}