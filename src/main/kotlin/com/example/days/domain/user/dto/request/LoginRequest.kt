package com.example.days.domain.user.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

data class LoginRequest(

    @NotBlank
    @Schema(
        description = "메일주소",
        example = "예시) example@email.com"
    )
    val email: String,

    @NotBlank
    @Schema(
        description = "비밀번호",
        example = "비밀번호는 8~16자의 영문 대소문자, 숫자, 특수문자로 이루어져야 합니다."
    )
    val password: String
)