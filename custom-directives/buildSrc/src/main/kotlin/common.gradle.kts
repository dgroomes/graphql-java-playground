// This is a "pre-compiled script plugin", or a "convention plugin". See the Gradle docs: https://docs.gradle.org/current/samples/sample_convention_plugins.html#compiling_convention_plugins

plugins {
    java
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

/**
 * Configure the compilation and test tasks to enable Java language "Preview Features". Specifically, we want the "JEP 406: Pattern Matching for switch"
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

repositories {
    mavenCentral()
}

val slf4jVersion = "1.7.36" // SLF4J releases: http://www.slf4j.org/news.html
val graphqlJavaVersion = "19.2" // GraphQL Java releases: https://github.com/graphql-java/graphql-java/releases
val http4kVersion = "4.30.9.0" // http4K releases: https://github.com/http4k/http4k/releases

dependencies {
    constraints {
        implementation("org.slf4j:slf4j-api:$slf4jVersion")
        runtimeOnly("org.slf4j:slf4j-simple:$slf4jVersion")

        implementation("com.graphql-java:graphql-java:$graphqlJavaVersion")

        implementation("org.http4k:http4k-core:$http4kVersion")
        implementation("org.http4k:http4k-server-netty:$http4kVersion")
        implementation("org.http4k:http4k-format-jackson:$http4kVersion")
    }
}
