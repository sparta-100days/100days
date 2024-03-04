package com.example.days.global.infra.regex

import org.springframework.stereotype.Component
import java.util.regex.Pattern

@Component
class RegexFunc {

    // userEmail
    fun regexUserEmail(userEmail: String): String {
        val email = "^([a-zA-Z0-9+-_.]{3,30})+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$"
        if (!Pattern.matches(email, userEmail)) throw IllegalArgumentException("이메일 형식이 올바르지 않습니다.") else return userEmail
    }

    // password
    fun regexPassword(userPassword: String): String {
        val pass = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[.!@#%^&*])[a-zA-Z0-9.!@#%^&*]{4,20}\$"
        if (!Pattern.matches(pass, userPassword)) throw IllegalArgumentException("비밀번호 형식이 올바르지 않습니다.") else return userPassword
    }

}