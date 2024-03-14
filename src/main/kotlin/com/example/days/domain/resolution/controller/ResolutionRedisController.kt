package com.example.days.domain.resolution.controller

import com.example.days.domain.resolution.dto.response.SearchLogRedis
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


@RestController
@RequestMapping("api/v2/resolution")
class ResolutionRedisController(
    @Qualifier("RedisResolutionV2") private val resolutionRedisService : ResolutionRedisService
) {

    @Operation(summary = "목표 검색")
    @GetMapping("/api/searchLog")
    fun searchByResolution(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @RequestParam title: String,
        @PageableDefault(size = 0, page = 10,sort = ["title"]) pageable: Pageable
    ): ResponseEntity<Page<SearchResponse>> {
        return ResponseEntity.status(HttpStatus.CREATED).body(resolutionRedisService.searchByResolution(title, pageable))
    }

    @Operation(summary = "최근 검색 기록: 저장")
    @PostMapping("/api/searchLog")
    fun saveRecentSearchLog(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @RequestParam title: String
    ): ResponseEntity<Unit> {
        val userId = userPrincipal.id
        return ResponseEntity.status(HttpStatus.CREATED).body(resolutionRedisService.saveRecentSearchLog(title, userId))
    }

    @Operation(summary = "최근 검색 기록: 조회")
    @GetMapping("/api/searchLog")
    fun findRecentSearchLog(
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<List<SearchLogRedis>?>{
        val userId = userPrincipal.id
        return ResponseEntity.status(HttpStatus.OK).body(resolutionRedisService.findRecentSearchLog(userId))
    }

    @Operation(summary = "최근 검색 기록: 삭제")
    @DeleteMapping("/api/searchLog")
    fun deleteRecentSearchLog(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @RequestParam title: String
    ): ResponseEntity<List<SearchLogRedis>?> {
        val userId = userPrincipal.id
        resolutionRedisService.deleteRecentSearchLog(userId,title)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

}