plugins {
    id("common")
    alias(libs.plugins.kotlin.jvm)
    application
}

dependencies {
    implementation(libs.slf4j.api)
    runtimeOnly(libs.slf4j.simple)

    implementation(project(":graphql"))

    implementation(libs.http4k.core)
    implementation(libs.http4k.server.netty)
    implementation(libs.http4k.format.jackson)
}

application {
    mainClass.set("dgroomes.server.Server")
}
