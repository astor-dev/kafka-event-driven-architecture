package com.astordev.ugc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan

@SpringBootApplication
@ConfigurationPropertiesScan

class ApiApplication

fun main(args: Array<String>) {
    runApplication<ApiApplication>(*args)
}