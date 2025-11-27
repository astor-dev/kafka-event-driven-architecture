dependencies {
    implementation(project(":common"))
    implementation(project(":domain"))

    implementation(project(":use-case:post-search"))

    implementation(project(":adapter:kafka"))
    implementation(project(":adapter:mysql"))
    implementation(project(":adapter:redis"))
    implementation(project(":adapter:elasticsearch"))
    implementation(project(":adapter:metadata-client"))
}