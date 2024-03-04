package com.example.days.domain.resolution.controller

import com.example.days.domain.resolution.dto.request.ResolutionRequest
import com.example.days.domain.resolution.dto.response.ResolutionResponse
import com.example.days.domain.resolution.model.Resolution
import com.example.days.domain.resolution.service.ResolutionService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/resolution")
class ResolutionController (
    private val resolutionService: ResolutionService
){

    // ^오^: 기본적인 CRUD 만 추가했습니다.

    @PostMapping
    fun createResolution(
        @Valid @RequestBody resolutionRequest: ResolutionRequest
    ):ResponseEntity<ResolutionResponse>{
        val createResolution = resolutionService.createResolution(resolutionRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body(createResolution)
    }

    @GetMapping("/{resolutionId}")
    fun getResolutionById(
        @PathVariable resolutionId: Long
    ): ResponseEntity<ResolutionResponse>{
        val resolution = resolutionService.getResolutionById(resolutionId)
        return ResponseEntity.ok(resolution)
    }
    @GetMapping
    fun getResolutionList(): ResponseEntity<List<ResolutionResponse>>{
        val resolutionList = resolutionService.getResolutionList()
        return ResponseEntity.ok(resolutionList)
    }

    @PatchMapping("/{resolutionId}")
    fun updateResolution(
        @PathVariable resolutionId: Long,
        @Valid @RequestBody resolutionRequest: ResolutionRequest
    ): ResponseEntity<ResolutionResponse>{
        val updateResolution = resolutionService.updateResolution(resolutionId, resolutionRequest)
        return ResponseEntity.ok(updateResolution)
    }

    @DeleteMapping("/{resolutionId}")
    fun deleteResolution(
        @PathVariable resolutionId: Long,
    ): ResponseEntity<Unit>{
        resolutionService.deleteResolution(resolutionId)
        return ResponseEntity.noContent().build()
    }

}