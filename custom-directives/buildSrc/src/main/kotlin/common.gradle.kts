// This is a "pre-compiled script plugin", or a "convention plugin". See the Gradle docs: https://docs.gradle.org/current/samples/sample_convention_plugins.html#compiling_convention_plugins

plugins {
    java
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

/**
 * Configure the compilation and test tasks to enable Java language "Preview Features". Specifically, we want the "JEP 406: Pattern Matching for switch"
 * preview feature. https://openjdk.java.net/jeps/406
 */
tasks {
    withType(JavaCompile::class.java) {
        options.compilerArgs.addAll(arrayOf("--enable-preview"))
    }

    withType(Test::class.java) {
        jvmArgs = listOf("--enable-preview")
        useJUnitPlatform()
    }
}

repositories {
    mavenCentral()
}
