package com.example.days.global.common.exception.common


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
    "본인은 본인을 신고할 수 없습니다!"
)

data class NotReportException(val status: String) : RuntimeException(
    "이 닉네임은 이미 밴이나 탈퇴처리되어 있어 신고할 수 없습니다!"
)

data class NotMessagesException(val status: String) : RuntimeException(
    "이 닉네임은 이미 밴이나 탈퇴처리되어 있어 쪽지를 보낼 수도 받을 수도 없습니다!"
)

data class AlreadyTenReportException(val nickname: String) : RuntimeException(
    "이 닉네임은 이미 10번 신고 처리되어 밴 처리 진행중입니다."
)

data class AlreadyBANException(val nickname: String) : RuntimeException(
    "이미 밴으로 처리된 계정입니다."
)

data class NotHaveSearchException(val title: String) : RuntimeException(
    "검색 결과가 존재하지 않습니다."
)


