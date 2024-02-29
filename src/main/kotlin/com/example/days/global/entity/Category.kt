package com.example.days.global.entity

import com.example.days.global.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "category")
class Category(
    @Column(name = "name")
    val categoryName: String,

    @Column(name = "info")
    val info: String
):BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}