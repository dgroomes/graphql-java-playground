// This is a "pre-compiled script plugin", or a "convention plugin". See the Gradle docs: https://docs.gradle.org/current/samples/sample_convention_plugins.html#compiling_convention_plugins

plugins {
    java
}

repositories {
    mavenCentral()
}

val slf4jVersion = "2.0.16" // SLF4J releases: http://www.slf4j.org/news.html
val graphqlJavaVersion = "20.9" // GraphQL Java releases: https://github.com/graphql-java/graphql-java/releases
val classGraphVersion = "4.8.175" // ClassGraph releases: https://github.com/classgraph/classgraph/releases

dependencies {
    constraints {
        implementation("org.slf4j:slf4j-api:$slf4jVersion")
        runtimeOnly("org.slf4j:slf4j-simple:$slf4jVersion")
        implementation("com.graphql-java:graphql-java:$graphqlJavaVersion")
        implementation("io.github.classgraph:classgraph:$classGraphVersion")
    }
}
