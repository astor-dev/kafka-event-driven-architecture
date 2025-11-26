package com.astordev.ugc

sealed class PostInspectionError {
    object CategoryNotFound: PostInspectionError()
    object InspectionResultBad: PostInspectionError()
}