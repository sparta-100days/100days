package com.example.days.domain.report.service

import com.example.days.domain.report.dto.request.UserReportRequest
import com.example.days.domain.report.dto.response.UserReportResponse
import com.example.days.domain.report.model.UserReport
import com.example.days.domain.report.model.UserReportStatus
import com.example.days.domain.report.repository.ReportRepository
import com.example.days.domain.user.model.Status
import com.example.days.domain.user.repository.UserRepository
import com.example.days.global.common.exception.common.*
import com.example.days.global.common.exception.user.UserNotFoundException
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.stereotype.Service

@Service
class ReportServiceImpl(
    private val userRepository: UserRepository,
    private val reportRepository: ReportRepository
) : ReportService {
    @Transactional
    override fun reportUser(req: UserReportRequest, userId: Long): UserReportResponse {
        val reportedUserNickname = userRepository.findByNickname(req.reportedUserNickname) ?: throw UserNotFoundException()
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User", userId)

        if (reportedUserNickname.status == Status.BAN || reportedUserNickname.status == Status.WITHDRAW || user.status == Status.BAN || user.status == Status.WITHDRAW) {
            throw NotReportException("이 닉네임은 이미 밴이나 탈퇴처리되어 있어 신고할 수 없습니다")
        }

        if (user.nickname == req.reportedUserNickname) {
            throw NotSelfReportException("본인은 본인을 신고할 수 없습니다.")
        }

        if(reportedUserNickname.countReport >= 10){
            throw AlreadyTenReportException("이 닉네임은 이미 10번 신고 처리되어 밴 처리 진행중입니다.")
        }

        if(req.reportStatus != UserReportStatus.SPAM && req.reportStatus != UserReportStatus.HARMFUL && req.reportStatus != UserReportStatus.ILLEGAL){
            throw HttpMessageNotReadableException("SPAM, HARMFUL, ILLEGAL 중에 선택해서 입력해주길 바랍니다!")
        }


        val report = reportRepository.save(
            UserReport(
                reporter = user,
                reportedUserId = reportedUserNickname,
                content = req.content,
                reportStatus = req.reportStatus
                )
        )
        report.reportUser()
        report.reportedUserId.countReport++
        userRepository.save(user)
        return UserReportResponse.from(report)
    }
}