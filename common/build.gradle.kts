plugins {
    id("java-library")
}


dependencies {
    api("com.fasterxml.jackson.core:jackson-databind:2.15.0")
    api("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.15.3")
    api("com.fasterxml.jackson.module:jackson-module-kotlin")
    api("io.arrow-kt:arrow-core:2.2.0")
}