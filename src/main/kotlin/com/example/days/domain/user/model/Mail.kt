package com.example.days.domain.user.model

import com.example.days.domain.user.dto.response.EmailResponse
import com.example.days.global.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "mail")
class Mail(

    @Column(name = "email")
    val email: String,

    @Column(name = "code")
    val code: String,

): BaseEntity() {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

}


