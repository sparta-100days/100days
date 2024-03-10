package com.example.days.domain.like.controller

import com.example.days.domain.like.dto.request.LikeRequest
import com.example.days.domain.like.service.LikeService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/likes")
class LikeController(
    private val likeService: LikeService
) {
    @Operation(summary = "좋아요 증가")
    @PostMapping
    fun insertLike(
        @RequestBody likeRequest: LikeRequest
    ): ResponseEntity<Unit>{
        return ResponseEntity.ok(likeService.insertLike(likeRequest))
    }

    @Operation(summary = "좋아요 취소")
    @DeleteMapping
    fun deleteLike(
        @RequestBody likeRequest: LikeRequest
    ): ResponseEntity<Unit>{
        likeService.deleteLike(likeRequest)
        return ResponseEntity.noContent().build()

    }
}