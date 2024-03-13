package com.example.days.global.support

import com.example.days.global.infra.regex.RegexFunc
import org.springframework.stereotype.Component

@Component
class EmailRandomCode(
    private val regexFunc: RegexFunc
) {

    private val codePattern: List<Char> =
        ('a'..'z') + ('A'..'Z') + ('0'..'9') + listOf('!', '@', '#', '%', '^', '&', '*')

    fun generateRandomCode(length: Int): String {
        var code: String
        do {
            code = (1..length)
                .map { codePattern.random() }
                .joinToString("")
        }  while (!regexFunc.isValidPassword(code))

        return code
    }
}