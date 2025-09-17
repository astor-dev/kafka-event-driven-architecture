package com.astordev.ugc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication


@SpringBootApplication
@ConfigurationPropertiesScan
class AutoInspectionWorkerApplication

fun main(args: Array<String>) {
    runApplication<AutoInspectionWorkerApplication>(*args)
}