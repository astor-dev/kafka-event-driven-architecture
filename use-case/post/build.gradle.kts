dependencies {
    implementation("jakarta.transaction:jakarta.transaction-api")
    implementation(project(":use-case:core"))
    implementation(project(":use-case:post-resolving-help"))
}