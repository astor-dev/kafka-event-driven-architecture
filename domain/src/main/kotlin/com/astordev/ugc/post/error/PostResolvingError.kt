package com.astordev.ugc.post.error

import com.astordev.ugc.DomainError

sealed interface PostResolvingError : DomainError {
    object UserNotFound : PostResolvingError
    object CategoryNotFound : PostResolvingError
    object PostNotFound : PostResolvingError
}