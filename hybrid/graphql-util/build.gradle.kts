plugins {
    `java-library`
    id("common")
}

dependencies {
    implementation(libs.slf4j.api)
    api(libs.graphql.java)
}
