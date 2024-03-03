package com.example.days.domain.user.model

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "users")
class User(

    @Column(name = "email")
    val email: String,

    @Column(name = "nickname")
    val nickName: String,

    @Column(name = "password")
    val password: String,

    @Column(name = "birth")
    val birth: LocalDate,

    @Column(name = "isdelete")
    val isDelete: Boolean,

    @Column(name = "status")
    val status: UserStatus,

    @Column(name = "role")
    val role: UserRole,
) {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

}