package com.astordev.ugc.error

import org.springframework.http.HttpStatus

data class DomainErrorDetail(
    val httpStatus: HttpStatus,
    val message: String
)