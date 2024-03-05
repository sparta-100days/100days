package com.example.days.domain.admin.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import org.hibernate.validator.constraints.Length

data class SignUpAdminRequest(
    @field: Length(min = 4, max = 15, message = "message = 닉네임은 반드시 작성해주세요")
    @JsonProperty("nickname")
    private val _nickname: String,

    @field: NotBlank
    @field: Pattern(
        regexp = "^[a-zA-Z0-9]{6,15}@100days\\.com\$",
        message = "이메일의 형식에 맞게 본인 회사 이메일(100days.com)로 입력해주세요"
    )
    @JsonProperty("email")
    private val _email: String,

    @field:Length(min = 4, max = 15, message = "비밀번호는 4자 이상, 15자 이하여야합니다.")
    @field:Pattern(
        regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@\$!%*#?&])[A-Za-z\\d@\$!%*#?&]{4,15}\$",
        message = "영문, 숫자, 특수문자를 포함한 4~15자리로 입력해주세요"
    )
    @JsonProperty("password")
    private val _password: String
) {
    val email: String
        get() = _email
    val password: String
        get() = _password
    val nickname: String
        get() = _nickname
}
