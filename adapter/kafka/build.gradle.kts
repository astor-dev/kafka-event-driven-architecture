plugins {
    id("java-library")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    api("org.springframework.kafka:spring-kafka")
}