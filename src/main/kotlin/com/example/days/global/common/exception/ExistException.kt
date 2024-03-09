package com.example.days.global.common.exception

data class EmailExistException(val email: String) : RuntimeException(
    "이미 존재하는 회사 이메일입니다."
)

data class NicknameExistException(val nickname: String) : RuntimeException(
    "이미 존재하는 회사 닉네임입니다."
)

data class NoSendMessagesException(val id: Long) : RuntimeException(
    "본인이 보낸 메시지함이 아닙니다!"
)

data class NoReceiverMessagesException(val id: Long) : RuntimeException(
    "본인이 받은 메시지함이 아닙니다!"
)

data class NotSelfReportException(val nickname: String) : RuntimeException(
    "본인은 본인을 신고할 수 없습니다"
)
