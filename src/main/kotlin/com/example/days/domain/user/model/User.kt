package com.example.days.domain.user.model

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "USERS")
class User(

    @Column(name = "EMAIL")
    val email: String,

    @Column(name = "NICKNAME")
    val nickName: String,

    @Column(name = "PASSWORD")
    val password: String,

    @Column(name = "BIRTH")
    val birth: LocalDate,

    @Column(name = "ISDELETE")
    val isDelete: Boolean,

    @Column(name = "STATUS")
    val status: UserStatus,
) {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

}