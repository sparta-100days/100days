package com.example.days.domain.messages.repository

import com.example.days.domain.messages.model.MessagesEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MessagesRepository : JpaRepository<MessagesEntity, Long> {
}