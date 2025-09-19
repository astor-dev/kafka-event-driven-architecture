package com.astordev.ugc

import com.sun.tools.javac.tree.TreeInfo.args
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class ContentSubscribingWorkerApplication

fun main(args: Array<String>) {
    runApplication<ContentSubscribingWorkerApplication>(*args)
}
