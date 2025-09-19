subprojects {
    dependencies {
        apply(plugin = "org.springframework.boot")
        apply(plugin = "org.jetbrains.kotlin.plugin.spring")

        implementation("org.springframework.boot:spring-boot-starter")
        annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    }
}