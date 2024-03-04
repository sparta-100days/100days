package com.example.days.domain.user.dto.request

import com.example.days.domain.user.model.UserRole
import com.example.days.domain.user.model.UserStatus
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import java.time.LocalDate

data class SignUpRequest(

    @NotBlank
    @Schema(
        description = "메일주소",
        example = "예시) example@email.com"
    )
    val email: String,

    @NotBlank
    @Schema(
        description = "이름",
        example = "이름은 영문 또는 한글로 이루어진 2~50자 사이로 작성해 주세요."
    )
    @field:Pattern(regexp = "^([a-zA-Zㄱ-ㅎ가-힣]{2,50})$")
    val nickName: String,

    @NotBlank
    @Schema(
        description = "비밀번호",
        example = "비밀번호는 8~16자의 영문 대소문자, 숫자, 특수문자로 이루어져야 합니다."
    )
    val password: String,

    @NotBlank
    @Schema(
        description = "비밀번호 재확인",
        example = "입력하신 비밀번호를 한번 더 입력해주세요."
    )
    val newPassword: String,

    val birth: LocalDate,

    @Schema(example = "회원상태")
    val status: UserStatus,

    @Schema(example = "회원등급")
    val role: UserRole
)