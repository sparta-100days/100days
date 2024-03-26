package com.example.days.domain.report.service

import com.example.days.domain.report.dto.request.CommentReportRequest
import com.example.days.domain.report.dto.request.PostReportRequest
import com.example.days.domain.report.dto.request.UserReportRequest
import com.example.days.domain.report.dto.response.CommentReportResponse
import com.example.days.domain.report.dto.response.PostReportResponse
import com.example.days.domain.report.dto.response.UserReportResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ReportService {

    // 유저 신고
    fun reportUser(req: UserReportRequest, userId: Long): UserReportResponse

    // 포스트 신고
    fun reportPost(req: PostReportRequest, userId: Long): PostReportResponse


    // 댓글 신고
    fun reportComment(req: CommentReportRequest, userId: Long): CommentReportResponse
}