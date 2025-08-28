subprojects {
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "org.jetbrains.kotlin.plugin.jpa")

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter:3.5.5");
        implementation(project(":common"))
        implementation(project(":domain"))
        implementation(project(":usecase:core"))
    }
}