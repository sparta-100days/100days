package com.example.days.domain.category.repository

import com.example.days.domain.category.model.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<Category, Long> {
    fun findByName(name: String): Category?

    fun existsByName(name: String): Boolean
}