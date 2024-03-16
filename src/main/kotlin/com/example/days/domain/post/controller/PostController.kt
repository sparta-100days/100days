package com.example.days.domain.post.controller

import com.example.days.domain.post.dto.request.PostRequest
import com.example.days.domain.post.dto.response.PostResponse
import com.example.days.domain.post.service.PostService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/posts")
@RestController
class PostController(
    private val postService: PostService
){
    @GetMapping
    fun getPostList(
    ): ResponseEntity<List<PostResponse>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.getPostList())
    }

    @GetMapping("/{postId}")
    fun getPost(
        @PathVariable postId: Long
    ): ResponseEntity<PostResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.getPost(postId))
    }

    @PostMapping
    fun creatPost(
        @RequestBody request: PostRequest
    ): ResponseEntity<PostResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.creatPost(request))
    }

    @PutMapping("/{postId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    fun updatePost(
        @PathVariable postId: Long,
        @RequestBody request: PostRequest
    ): ResponseEntity<PostResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.updatePost(postId, request))
    }

    @DeleteMapping("/{postId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    fun deletePost(
        @PathVariable postId: Long
    ) : ResponseEntity<Unit> {
        //postService.deletePost(postId)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }
}