dependencies {
    implementation("jakarta.transaction:jakarta.transaction-api")
    implementation(project(":usecase:core"))
    implementation(project(":usecase:post-resolving-help"))
}