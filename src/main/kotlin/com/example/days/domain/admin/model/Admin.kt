package com.example.days.domain.admin.model

import jakarta.persistence.*

@Entity
@Table(name = "admin")
class Admin(

    @Column(name = "nickname")
    val nickname: String,

    @Column(name = "email")
    val email: String,

    @Column(name = "password")
    val password: String,

    @Column(name = "deleted")
    val deleted: Boolean = false

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}