package com.example.days.domain.user.model

import com.example.days.domain.user.dto.request.ModifyInfoRequest
import com.example.days.global.entity.BaseEntity
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "users")
class User(

    @Column(name = "email")
    var email: String,

    @Column(name = "nickname")
    var nickname: String,

    @Column(name = "password")
    var password: String,

    @Column(name = "birth")
    var birth: LocalDate,

    @Column(name = "isdelete")
    var isDelete: Boolean,

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    var status: Status,

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    val role: UserRole,

): BaseEntity() {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun userDeleteByAdmin() {
        status = Status.WITHDRAW
    }

    fun userIsDeletedByAdmin() {
        isDelete = true
    }
}


