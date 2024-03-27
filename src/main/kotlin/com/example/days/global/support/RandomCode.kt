package com.example.days.global.support

import com.example.days.global.infra.regex.RegexFunc
import com.example.days.global.infra.security.PasswordEncoderConfig
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class RandomCode(
    private val regexFunc: RegexFunc
) {
    // 특수문자를 리스트로 생성 후 String으로 변환함
    private fun codePattern(): String {
        val pattern = listOf('!', '@', '#', '%', '^', '&', '*')
        return pattern.toString()
    }

    private val randomPass = UUID.randomUUID().toString() // UUID 로 랜덤문자 생성
        .let { it + codePattern().random() } // 아래에 생성한 특문과 위의 랜덤문자 혼합

    // 위에 생성된 문자를 지정한 유효성 검증을 통과할때만 사용하게 하기
    fun generateRandomCode(length: Int): String {
        var code: String
        do {
            code = (1..length)
                .map { randomPass.random() }
                .joinToString("")
        } while (!regexFunc.isValidPassword(code))

        return code
    }
}