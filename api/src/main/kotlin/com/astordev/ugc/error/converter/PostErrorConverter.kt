package com.astordev.ugc.error.converter

import com.astordev.ugc.PostDeleteError
import com.astordev.ugc.PostResolvingError
import com.astordev.ugc.PostUpdateError
import com.astordev.ugc.error.DomainErrorDetail
import org.springframework.http.HttpStatus


fun PostUpdateError.extractErrorDetail(): DomainErrorDetail {
    when (this) {
        is PostUpdateError.PostNotFound -> {
            return DomainErrorDetail(
                httpStatus = HttpStatus.NOT_FOUND,
                message = "Post $id not found",
            )
        }
    }
}

fun PostDeleteError.extractErrorDetail(): DomainErrorDetail {
    when (this) {
        is PostDeleteError.PostNotFound -> {
            return DomainErrorDetail(
                httpStatus = HttpStatus.NOT_FOUND,
                message = "Post $id not found"
            )
        }
    }
}

fun PostResolvingError.extractErrorDetail(): DomainErrorDetail {
    when (this) {
        is PostResolvingError.UserNotFound -> {
            return DomainErrorDetail(
                httpStatus = HttpStatus.NOT_FOUND,
                message = "User $id not found"
            )
        }
        is PostResolvingError.CategoryNotFound -> {
            return DomainErrorDetail(
                httpStatus = HttpStatus.NOT_FOUND,
                message = "Category $id not found"
            )
        }
        is PostResolvingError.PostNotFound -> {
            return DomainErrorDetail(
                httpStatus = HttpStatus.NOT_FOUND,
                message = "Post $id not found"
            )
        }
    }
}
