dependencies {
    implementation(project(":common"))
    implementation(project(":domain"))

    implementation(project(":use-case:post-resolving-help"))

    implementation(project(":adapter:kafka"))
    implementation(project(":adapter:mysql"))
    implementation(project(":adapter:redis"))
    implementation(project(":adapter:metadata-client"))
}