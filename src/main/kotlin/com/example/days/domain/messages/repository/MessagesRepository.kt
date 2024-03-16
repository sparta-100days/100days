package com.example.days.domain.messages.repository

import com.example.days.domain.messages.model.MessagesEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface MessagesRepository : JpaRepository<MessagesEntity, Long>{
    fun findAllBySenderIdAndDeletedBySenderFalseOrderByIdDesc(pageable: Pageable, senderId: Long): Page<MessagesEntity>

    fun findAllByReceiverIdAndDeletedByReceiverFalseOrderByIdDesc(pageable: Pageable, receiverId: Long): Page<MessagesEntity>
}