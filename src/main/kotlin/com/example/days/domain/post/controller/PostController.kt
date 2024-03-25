package com.example.days.domain.post.controller

import com.example.days.domain.post.dto.request.PostRequest
import com.example.days.domain.post.dto.response.DeleteResponse
import com.example.days.domain.post.dto.response.PostListResponse
import com.example.days.domain.post.dto.response.PostResponse
import com.example.days.domain.post.dto.response.PostWithCommentResponse
import com.example.days.domain.post.model.PostType
import com.example.days.domain.post.service.PostService
import com.example.days.global.infra.security.UserPrincipal
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/posts")
@RestController
class PostController(
    private val postService: PostService
) {

    @Operation(summary = "포스트 목록조회")
    @GetMapping
    fun getAllPostList(): ResponseEntity<List<PostListResponse>> {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPostList())
    }

    @Operation(summary = "포스트 단건조회")
    @GetMapping("/{postId}")
    fun getPost(@PathVariable postId: Long): ResponseEntity<PostWithCommentResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostById(postId))
    }

    @Operation(summary = "포스트 작성")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    fun creatPost(
        @AuthenticationPrincipal userId: UserPrincipal,
        categoryId: Long,
        resolutionId: Long,
        type: PostType,
        @RequestBody request: PostRequest
    ): ResponseEntity<PostResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(postService.createPost(userId, categoryId, resolutionId, type, request))
    }

    @Operation(summary = "포스트 수정")
    @PutMapping("/{postId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    fun updatePost(
        @AuthenticationPrincipal userId: UserPrincipal,
        type: PostType,
        @PathVariable postId: Long,
        @RequestBody request: PostRequest
    ): ResponseEntity<PostResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(postService.updatePost(userId, type, postId, request))
    }

    @Operation(summary = "포스트 삭제")
    @DeleteMapping("/{postId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    fun deletePost(
        @AuthenticationPrincipal userId: UserPrincipal,
        @PathVariable postId: Long
    ): ResponseEntity<DeleteResponse> {
        postService.deletePost(userId, postId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}