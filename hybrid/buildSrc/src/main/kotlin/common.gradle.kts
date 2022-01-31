// This is a "pre-compiled script plugin", or a "convention plugin". See the Gradle docs: https://docs.gradle.org/current/samples/sample_convention_plugins.html#compiling_convention_plugins

plugins {
    java
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

repositories {
    mavenCentral()
}

val slf4jVersion = "1.7.32" // SLF4J releases: http://www.slf4j.org/news.html
val graphqlJavaVersion = "17.3" // GraphQL Java releases: https://github.com/graphql-java/graphql-java/releases
val classGraphVersion = "4.8.138" // ClassGraph releases: https://github.com/classgraph/classgraph/releases

dependencies {
    constraints {
        implementation("org.slf4j:slf4j-api:$slf4jVersion")
        runtimeOnly("org.slf4j:slf4j-simple:$slf4jVersion")
        implementation("com.graphql-java:graphql-java:$graphqlJavaVersion")
        implementation("io.github.classgraph:classgraph:$classGraphVersion")
    }
}
