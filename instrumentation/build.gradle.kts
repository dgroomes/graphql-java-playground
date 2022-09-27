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
val graphqlJavaVersion = "19.2" // GraphQL Java releases: https://github.com/graphql-java/graphql-java/releases
val jacksonVersion = "2.13.4" // Jackson releases: https://github.com/FasterXML/jackson/wiki/Jackson-Releases

dependencies {
    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    runtimeOnly("org.slf4j:slf4j-simple:$slf4jVersion")
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    implementation("com.graphql-java:graphql-java:$graphqlJavaVersion")
}

application {
    mainClass.set("dgroomes.Cli")
}
