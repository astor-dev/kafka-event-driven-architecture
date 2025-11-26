package com.astordev.ugc.error.converter

import com.astordev.ugc.error.DomainErrorDetail
import com.astordev.ugc.post.error.PostResolvingError
import org.springframework.http.HttpStatus

fun PostResolvingError.extractErrorDetail(): DomainErrorDetail {
    when (this) {
        is PostResolvingError.UserNotFound -> {
            return DomainErrorDetail(
                httpStatus = HttpStatus.NOT_FOUND,
                message = "User not found"
            )
        }
        is PostResolvingError.CategoryNotFound -> {
            return DomainErrorDetail(
                httpStatus = HttpStatus.NOT_FOUND,
                message = "Category not found"
            )
        }
        is PostResolvingError.PostNotFound -> {
            return DomainErrorDetail(
                httpStatus = HttpStatus.NOT_FOUND,
                message = "Post not found"
            )
        }
    }
}
