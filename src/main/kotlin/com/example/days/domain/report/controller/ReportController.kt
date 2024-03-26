package com.example.days.domain.report.controller

import com.example.days.domain.report.dto.request.CommentReportRequest
import com.example.days.domain.report.dto.request.PostReportRequest
import com.example.days.domain.report.dto.request.UserReportRequest
import com.example.days.domain.report.dto.response.CommentReportResponse
import com.example.days.domain.report.dto.response.PostReportResponse
import com.example.days.domain.report.dto.response.UserReportResponse
import com.example.days.domain.report.service.ReportService
import com.example.days.global.infra.security.UserPrincipal
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/reports")
class ReportController(
    private val reportService: ReportService
) {

    @Operation(summary = "유저 신고 기능")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/users")
    fun reportUser(
        @RequestBody req: UserReportRequest,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<UserReportResponse> {
        val userId = userPrincipal.subject
        return ResponseEntity.status(HttpStatus.CREATED).body(reportService.reportUser(req, userId))
    }

    @Operation(summary = "포스트 신고 기능")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/posts")
    fun reportPost(
        @RequestBody req: PostReportRequest,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<PostReportResponse> {
        val userId = userPrincipal.subject
        return ResponseEntity.status(HttpStatus.CREATED).body(reportService.reportPost(req, userId))
    }

    @Operation(summary = "댓글 신고 기능")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/comments")
    fun reportComment(
        @RequestBody req: CommentReportRequest,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<CommentReportResponse> {
        val userId = userPrincipal.subject
        return ResponseEntity.status(HttpStatus.CREATED).body(reportService.reportComment(req, userId))
    }
}