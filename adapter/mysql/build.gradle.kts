plugins {
    id("java-library")
    kotlin("plugin.jpa") version "2.2.10"

}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("mysql:mysql-connector-java:8.0.33")
}