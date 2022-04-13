plugins {
    application
}

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

val slf4jVersion = "1.7.36" // SLF4J releases: http://www.slf4j.org/news.html
val graphqlJavaVersion = "18.0" // GraphQL Java releases: https://github.com/graphql-java/graphql-java/releases

dependencies {
    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    runtimeOnly("org.slf4j:slf4j-simple:$slf4jVersion")

    implementation("com.graphql-java:graphql-java:$graphqlJavaVersion")
}

application {
    mainClass.set("dgroomes.EchoGraphql")
}
