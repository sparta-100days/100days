package com.example.days.domain.resolution.controller

import com.example.days.domain.resolution.dto.request.ResolutionRequest
import com.example.days.domain.resolution.dto.response.ResolutionResponse
import com.example.days.domain.resolution.service.ResolutionService
import com.example.days.global.common.SortOrder
import com.example.days.global.infra.security.UserPrincipal
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/resolution")
class ResolutionController (
    private val resolutionService: ResolutionService
){

    // ^오^: 기본적인 CRUD 만 추가했습니다.

    @Operation(summary = "목표 생성")
    @PostMapping
    fun createResolution(
        @Valid @RequestBody resolutionRequest: ResolutionRequest,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ):ResponseEntity<ResolutionResponse>{
        val userId = userPrincipal.id
        val createResolution = resolutionService.createResolution(resolutionRequest, userId)

        return ResponseEntity.status(HttpStatus.CREATED).body(createResolution)
    }

    @Operation(summary = "목표 단건 조회")
    @GetMapping("/{resolutionId}")
    fun getResolutionById(
        @PathVariable resolutionId: Long
    ): ResponseEntity<ResolutionResponse>{
        val resolution = resolutionService.getResolutionById(resolutionId)
        return ResponseEntity.ok(resolution)
    }
    @Operation(summary = "목표 전체 조회(페이징)")
    @GetMapping
    fun getResolutionListPagenated(
        @RequestParam(defaultValue = "0") page: Int,
        sortOrder: SortOrder?
    ): ResponseEntity<Page<ResolutionResponse>>{
        val resolutionList = resolutionService.getResolutionListPaginated(page, sortOrder)
        return ResponseEntity.ok(resolutionList)
    }

    @Operation(summary = "목표 수정")
    @PatchMapping("/{resolutionId}")
    fun updateResolution(
        @PathVariable resolutionId: Long,
        @Valid @RequestBody resolutionRequest: ResolutionRequest,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<ResolutionResponse>{
        val userId = userPrincipal.id
        val updateResolution = resolutionService.updateResolution(resolutionId, userId ,resolutionRequest)
        return ResponseEntity.ok(updateResolution)
    }

    @Operation(summary = "목표 삭제")
    @DeleteMapping("/{resolutionId}")
    fun deleteResolution(
        @PathVariable resolutionId: Long,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<Unit>{
        val userId = userPrincipal.id
        resolutionService.deleteResolution(resolutionId, userId)
        return ResponseEntity.noContent().build()
    }

}