package com.example.days.domain.resolution.model

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import jakarta.persistence.*

@Entity
@Table(name = "search_Log")
class SearchLog(
    @Column(name = "title", nullable = false)
    var title: String,

    @JsonSerialize(using = LocalDateTimeSerializer::class)
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    @Column(name = "createdAt", nullable = false, updatable = false)
    var createdAt: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long? =null
}