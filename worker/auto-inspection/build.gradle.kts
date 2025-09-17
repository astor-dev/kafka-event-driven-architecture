apply(plugin = "org.springframework.boot")
apply(plugin = "org.jetbrains.kotlin.plugin.spring")

dependencies {
    implementation(project(":common"))
    implementation(project(":domain"))

    implementation(project(":usecase:core"))
    implementation(project(":usecase:inspected-post"))

    implementation(project(":adapter:kafka"))
    implementation(project(":adapter:metadata-client"))
    implementation(project(":adapter:chat-gpt-client"))

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
}