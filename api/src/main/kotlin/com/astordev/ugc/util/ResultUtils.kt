package com.astordev.ugc.util

import com.astordev.ugc.Result

inline fun <T, E, R> Result<T, E>.mapBoth(
    onSuccess: (T) -> R,
    onFailure: (E) -> R
): R = when (this) {
    is Result.Success -> onSuccess(data)
    is Result.Failure -> onFailure(error)
}
