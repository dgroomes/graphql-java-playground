plugins {
    `java-library`
    id("common")
}

dependencies {
    implementation(libs.slf4j.api)
    implementation(project(":graphql-extensions"))
    implementation(project(":woodlands"))
    implementation(project(":java-utility"))
    api(libs.graphql.java)
}
