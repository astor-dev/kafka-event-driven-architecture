import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("java")
    id("org.springframework.boot") version "3.5.5" apply false
    kotlin("jvm") version "2.2.10" apply false
    kotlin("plugin.spring") version "2.2.10" apply false
}

group = "com.astordev.ugc"
version = "0.0.1-SNAPSHOT"

subprojects {
    apply(plugin = "java")
    apply(plugin = "idea")
    apply(plugin = "org.jetbrains.kotlin.jvm")


    repositories {
        mavenCentral()
    }

    configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_21
        toolchain {
            languageVersion = JavaLanguageVersion.of(21)
        }
    }


    dependencies {
        implementation(platform(project.dependencies.create("org.springframework.boot:spring-boot-dependencies:3.5.5")))
        annotationProcessor(platform(project.dependencies.create("org.springframework.boot:spring-boot-dependencies:3.5.5")))
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        compilerOptions {
            freeCompilerArgs.add("-Xjsr305=strict")
            freeCompilerArgs.add("-Xcontext-parameters")
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }


    tasks.named<Test>("test") {
        useJUnitPlatform()
    }
}
