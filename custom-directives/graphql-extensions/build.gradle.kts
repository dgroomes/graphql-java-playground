plugins {
    `java-library`
    id("common")
}

dependencies {
    implementation("org.slf4j:slf4j-api")
    api("com.graphql-java:graphql-java")
}
