plugins {
    id("common")
    kotlin("jvm") version "1.6.10" // Kotlin releases: https://kotlinlang.org/docs/releases.html#release-details
    application
}

/**
 * Configure the 'application' tasks to enable Java language "Preview Features". Specifically, we want the "JEP 406: Pattern Matching for switch"
 * preview feature. https://openjdk.java.net/jeps/406
 */
tasks {
    withType(JavaCompile::class.java) {
        options.compilerArgs.addAll(arrayOf("--enable-preview"))
    }

    withType(Test::class.java) {
        jvmArgs = listOf("--enable-preview")
        useJUnitPlatform()
    }
}

dependencies {
    implementation("org.slf4j:slf4j-api")
    runtimeOnly("org.slf4j:slf4j-simple")

    implementation(project(":java-utility"))
    implementation(project(":graphql-extensions"))
    implementation(project(":woodlands"))

    implementation("org.http4k:http4k-core:version")
    implementation("org.http4k:http4k-server-netty")
    implementation("org.http4k:http4k-format-jackson")
}

application {
    mainClass.set("dgroomes.server.Server")
}
