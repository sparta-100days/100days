package com.example.days.domain.resolution.controller

import com.example.days.domain.resolution.dto.response.SearchLogRedisResponse
import com.example.days.domain.resolution.dto.response.SearchResponse
import com.example.days.domain.resolution.service.ResolutionRedisService
import com.example.days.global.infra.security.UserPrincipal
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime


@RestController
@RequestMapping("/api/v2/resolution")
class ResolutionRedisController(
    @Qualifier("RedisResolutionV2") private val resolutionRedisService : ResolutionRedisService
) {

    @Operation(summary = "목표 검색")
    @GetMapping("/search")
    fun searchByResolution(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @RequestParam title: String,
        @PageableDefault(size = 10, sort = ["title"]) pageable: Pageable
    ): ResponseEntity<Page<SearchResponse>> {
        return ResponseEntity.status(HttpStatus.CREATED).body(resolutionRedisService.searchByResolution(title, pageable))
    }

    @Operation(summary = "최근 검색 기록: 저장")
    @PostMapping("/searchLog")
    fun saveRecentSearchLog(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @RequestParam title: String
    ): ResponseEntity<Unit> {
        val userId = userPrincipal.id
        return ResponseEntity.status(HttpStatus.CREATED).body(resolutionRedisService.saveRecentSearchLog(title, userId))
    }

    @Operation(summary = "최근 검색 기록: 조회")
    @GetMapping("/searchLog")
    fun findRecentSearchLog(
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<List<SearchLogRedisResponse>>{
        val userId = userPrincipal.id
        return ResponseEntity.status(HttpStatus.OK).body(resolutionRedisService.findRecentSearchLog(userId))
    }

    @Operation(summary = "최근 검색 기록: 삭제")
    @DeleteMapping("/searchLog")
    fun deleteRecentSearchLog(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @RequestParam title: String,
        @RequestParam createdAt: LocalDateTime,
    ): ResponseEntity<List<SearchLogRedisResponse>?> {
        val userId = userPrincipal.id
        resolutionRedisService.deleteRecentSearchLog(userId,title,createdAt)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

}