plugins {
    id("java-library")
}

apply(plugin = "org.jetbrains.kotlin.plugin.spring")

dependencies {
    api("com.fasterxml.jackson.core:jackson-databind:2.15.0")
    api("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.15.3")
}