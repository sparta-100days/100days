package com.example.days.domain.user.controller

import com.example.days.domain.user.dto.request.EmailRequest
import com.example.days.domain.user.dto.response.EmailResponse
import com.example.days.domain.user.service.UserMailService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/mail")
class UserMailController(
    val userMailService: UserMailService
) {

    @PostMapping("/sendmail")
    fun mailSend(@RequestBody request: EmailRequest): ResponseEntity<Unit> {
        userMailService.sendEmail(request)
        return ResponseEntity.status(HttpStatus.OK).build()
    }

    @GetMapping("/verifycode")
    fun verifyCode(@RequestParam("code") code: String): ResponseEntity<Unit> {
        userMailService.verifyCode(code)
        return ResponseEntity.status(HttpStatus.OK).build()
    }

}