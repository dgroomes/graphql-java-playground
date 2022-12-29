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

dependencies {
    implementation(libs.slf4j.api)
    runtimeOnly(libs.slf4j.simple)

    implementation(libs.graphql.java)
}

application {
    mainClass.set("dgroomes.EchoGraphql")
}
