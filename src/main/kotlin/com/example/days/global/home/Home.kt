package com.example.days.global.home

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Home {
    @GetMapping("/")
    fun home() = "반갑습니다??? 작심백일입니다!!"
}