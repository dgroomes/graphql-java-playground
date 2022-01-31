plugins {
    id("common")
    application
}

/**
 * Configure the compiler task, test task, start script creation task, and the run task to enable Java language "Preview
 * Features". Specifically, we want the "JEP 406: Pattern Matching for switch" preview feature. https://openjdk.java.net/jeps/406
 */
tasks {
    withType(JavaCompile::class.java) {
        options.compilerArgs.addAll(arrayOf("--enable-preview"))
    }

    withType(Test::class.java) {
        jvmArgs = listOf("--enable-preview")
        useJUnitPlatform()
    }

    named<CreateStartScripts>("startScripts") {
        defaultJvmOpts = listOf("--enable-preview")
    }

    named<JavaExec>("run") {
        jvmArgs = listOf("--enable-preview")
    }
}

dependencies {
    implementation("org.slf4j:slf4j-api")
    runtimeOnly("org.slf4j:slf4j-simple")
    implementation(project(":graphql-util"))
    implementation(project(":java-info"))
}

application {
    mainClass.set("dgroomes.cli.Cli")
}
