package com.astordev.ugc.error

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

data class DomainErrorDetail(
    val httpStatus: HttpStatus,
    val message: String,
)

fun DomainErrorDetail.thenThrow(): Nothing {
    throw ResponseStatusException(this.httpStatus, this.message)
}

fun DomainErrorDetail.log(logger: org.slf4j.Logger) {
    logger.error("Error occurred: HTTP ${this.httpStatus.value()} - ${this.message}")
}