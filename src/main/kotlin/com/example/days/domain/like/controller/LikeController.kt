package com.example.days.domain.like.controller

import com.example.days.domain.like.dto.request.LikeRequest
import com.example.days.domain.like.service.LikeService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/likes")
class LikeController(
    private val likeService: LikeService
) {
    @PostMapping
    fun insertLike(
        @RequestBody likeRequest: LikeRequest
    ): ResponseEntity<Unit>{
        return ResponseEntity.ok(likeService.insertLike(likeRequest))
    }

    @DeleteMapping
    fun deleteLike(
        @RequestBody likeRequest: LikeRequest
    ): ResponseEntity<Unit>{
        likeService.deleteLike(likeRequest)
        return ResponseEntity.noContent().build()

    }
}