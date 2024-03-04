package com.example.days.domain.category.controller

import com.example.days.domain.category.dto.request.CategoryRequest
import com.example.days.domain.category.dto.request.CategoryUpdateRequest
import com.example.days.domain.category.dto.response.CategoryResponse
import com.example.days.domain.category.service.CategoryService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("categories")
class CategoryController(
    private val categoryService: CategoryService
) {

    @GetMapping
    fun getCategoryList(): ResponseEntity<List<CategoryResponse>> {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getCategoryList())
    }

    @PostMapping
    fun createCategory(
        @RequestBody req: CategoryRequest
    ): ResponseEntity<CategoryResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(req))
    }

    @PutMapping("/{categoryId}")
    fun updateCategory(@PathVariable categoryId: Long, req: CategoryUpdateRequest): ResponseEntity<CategoryResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.updateCategory(categoryId, req))
    }

    @DeleteMapping("/{categoryId}")
    fun deleteCategory(@PathVariable categoryId: Long): ResponseEntity<Unit> {
        categoryService.deleteCategory(categoryId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}