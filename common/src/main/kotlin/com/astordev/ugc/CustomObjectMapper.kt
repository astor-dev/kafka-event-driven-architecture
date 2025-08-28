package com.astordev.ugc

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule

open class CustomObjectMapper : ObjectMapper() {
    init {
        registerModule(JavaTimeModule())
    }
}