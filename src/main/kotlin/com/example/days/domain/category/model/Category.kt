package com.example.days.domain.category.model

import com.example.days.domain.category.repository.CategoryRepository
import com.example.days.global.common.exception.common.NicknameExistException
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime

@Entity
@Table(name = "category")
class Category(
    @Column(name = "name")
    val name: String,

    @Column(name = "info")
    var info: String

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    //ㅇㅅㅇbaseEntity를 사용하려 했으나 이는 논리적 삭제가 추가할 필요성을 못느껴서?,, updatedAt는 추후에 필요하면 추가하기 위해서 일단 추가 안함.
    @CreatedDate
    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()
}

fun checkingNameExists(name: String, categoryRepository: CategoryRepository) {
    if (categoryRepository.existsByName(name)) {
        throw NicknameExistException(name)
    }
}