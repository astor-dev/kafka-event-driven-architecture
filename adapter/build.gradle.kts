subprojects {
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")

    dependencies {
        implementation(project(":common"))
        implementation(project(":domain"))
        implementation(project(":usecase:core"))

        implementation("org.springframework:spring-context")
        implementation("org.slf4j:slf4j-api")
    }
}