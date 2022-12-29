plugins {
    id("common")
    `java-library`
}

dependencies {
    implementation(libs.slf4j.api)
    implementation(libs.classgraph)
}
