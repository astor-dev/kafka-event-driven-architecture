apply(plugin = "org.springframework.boot")
apply(plugin = "org.jetbrains.kotlin.plugin.spring")

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.11")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    implementation(project(":common"))
    implementation(project(":domain"))
//
    implementation(project(":use-case:post"))
//    implementation(project(":use-case:inspected-post"))
    implementation(project(":use-case:subscribing-post"))
//    implementation(project(":use-case:post-search-usecase"))
//    implementation(project(":use-case:coupon-usecase"))
//
    implementation(project(":adapter:kafka"))
    implementation(project(":adapter:mysql"))
    implementation(project(":adapter:mongodb"))
    implementation(project(":adapter:redis"))
    implementation(project(":adapter:elasticsearch"))
    implementation(project(":adapter:metadata-client"))
//    implementation(project(":adapter:chat-gpt-client"))
}