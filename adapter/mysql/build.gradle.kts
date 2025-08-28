plugins {
    id("java-library")
}

apply(plugin = "org.jetbrains.kotlin.plugin.jpa")

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.5.5")
    implementation("mysql:mysql-connector-java:8.0.33")
}