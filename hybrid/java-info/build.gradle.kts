plugins {
    id("common")
    `java-library`
}

dependencies {
    implementation("org.slf4j:slf4j-api")
    implementation("io.github.classgraph:classgraph")
}
