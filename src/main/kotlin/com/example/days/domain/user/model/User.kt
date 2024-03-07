package com.example.days.domain.user.model

import com.example.days.global.entity.BaseEntity
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "users")
class User(

    @Column(name = "email")
    val email: String,

    @Column(name = "nickname")
    val nickname: String,

    @Column(name = "password")
    val password: String,

    @Column(name = "birth")
    val birth: LocalDate,

    @Column(name = "isdelete")
    val isDelete: Boolean,

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    var status: UserStatus,

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    val role: UserRole,

): BaseEntity()  {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

}