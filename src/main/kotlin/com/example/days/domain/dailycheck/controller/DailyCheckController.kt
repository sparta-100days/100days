package com.example.days.domain.dailycheck.controller

import com.example.days.domain.dailycheck.dto.request.DailyCheckRequest
import com.example.days.domain.dailycheck.dto.response.DailyCheckResponse
import com.example.days.domain.dailycheck.service.DailyCheckService
import com.example.days.global.infra.security.UserPrincipal
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/daily_check/{resolutionId}")
class DailyCheckController(
    private val dailyCheckService: DailyCheckService
) {
    @Operation(summary = "데일리체크 생성")
    @PostMapping
    fun createDailyCheck(
        @PathVariable resolutionId: Long,
        @RequestBody dailyCheckRequest: DailyCheckRequest,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<DailyCheckResponse>{
        val userId = userPrincipal.subject
        val createdDailyCheck = dailyCheckService.createDailyCheck(resolutionId, userId, dailyCheckRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDailyCheck)
    }

    @Operation(summary = "데일리체크 조회")
    @GetMapping
    fun getDailyCheck(
        @PathVariable resolutionId: Long,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<List<DailyCheckResponse>>{
        val userId = userPrincipal.subject
        val dailyCheck = dailyCheckService.getDailyCheckByList(resolutionId, userId)
        return ResponseEntity.ok(dailyCheck)
    }

    @Operation(summary = "데일리체크 수정")
    @PatchMapping("/{dailyCheckId}")
    fun updateDailyCheckMemo(
        @PathVariable resolutionId: Long,
        @PathVariable dailyCheckId: Long,
        @RequestBody dailyCheckRequest: DailyCheckRequest,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<DailyCheckResponse>{
        val userId = userPrincipal.subject
        val updatedDailyCheck = dailyCheckService.updateDailyCheck(resolutionId, userId, dailyCheckId, dailyCheckRequest)
        return ResponseEntity.ok(updatedDailyCheck)
    }

    @Operation(summary = "데일리체크 삭제")
    @DeleteMapping("/{dailyCheckId}")
    fun deleteDailyCheck(
        @PathVariable resolutionId: Long,
        @PathVariable dailyCheckId: Long,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<Unit>{
        val userId = userPrincipal.subject
        dailyCheckService.deleteDailyCheck(resolutionId, dailyCheckId, userId)
        return ResponseEntity.noContent().build()
    }
}