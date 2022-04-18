plugins {
    `java-library`
    id("common")
}

dependencies {
    implementation("org.slf4j:slf4j-api")
    implementation(project(":graphql-extensions"))
    implementation(project(":woodlands"))
    implementation(project(":java-utility"))
    api("com.graphql-java:graphql-java")
}
