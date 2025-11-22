package com.astordev.ugc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication


@SpringBootApplication
@ConfigurationPropertiesScan
class ContentIndexingWorkerApplication

fun main(args: Array<String>) {
    runApplication<ContentIndexingWorkerApplication>(*args)
}