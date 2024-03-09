package com.example.days.domain.messages.repository

import com.example.days.domain.messages.model.AdminMessagesEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface AdminMessagesRepository: JpaRepository<AdminMessagesEntity, Long> {
    fun findAllByReceiverIdAndDeletedByReceiverFalseOrderByIdDesc(pageable: Pageable, userId: Long): Page<AdminMessagesEntity>
}
