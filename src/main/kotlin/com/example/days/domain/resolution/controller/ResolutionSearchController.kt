package com.example.days.domain.resolution.controller

import com.example.days.domain.resolution.dto.response.SearchLogSearchResponse
import com.example.days.domain.resolution.dto.response.SearchResponse
import com.example.days.domain.resolution.service.ResolutionSearchService
import com.example.days.global.infra.security.UserPrincipal
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime


@RestController
@RequestMapping("/api/v2/resolution")
class ResolutionSearchController(
    @Qualifier("RedisResolutionV2") private val resolutionSearchService : ResolutionSearchService
) {

    @Operation(summary = "목표 검색")
    @GetMapping("/search")
    fun searchByResolution(
        @RequestParam title: String,
        @PageableDefault(size = 10, sort = ["title"]) pageable: Pageable
    ): ResponseEntity<Page<SearchResponse>> {
        return ResponseEntity.status(HttpStatus.CREATED).body(resolutionSearchService.searchByResolution(title, pageable))
    }

    @Operation(summary = "최근 검색 기록: 저장")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping("/searchLog")
    fun saveRecentSearchLog(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @RequestParam title: String
    ): ResponseEntity<Unit> {
        val userId = userPrincipal.id
        return ResponseEntity.status(HttpStatus.CREATED).body(resolutionSearchService.saveRecentSearchLog(title, userId))
    }

    @Operation(summary = "최근 검색 기록: 조회")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/searchLog")
    fun findRecentSearchLog(
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<List<SearchLogSearchResponse>>{
        val userId = userPrincipal.id
        return ResponseEntity.status(HttpStatus.OK).body(resolutionSearchService.findRecentSearchLog(userId))
    }

    @Operation(summary = "최근 검색 기록: 삭제")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @DeleteMapping("/searchLog")
    fun deleteRecentSearchLog(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @RequestParam title: String,
        @RequestParam createdAt: LocalDateTime,
    ): ResponseEntity<List<SearchLogSearchResponse>?> {
        val userId = userPrincipal.id
        resolutionSearchService.deleteRecentSearchLog(userId,title,createdAt)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

}