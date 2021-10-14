plugins {
    application
}

repositories {
    mavenCentral()
}

val graphqlJavaVersion = "17.3" // releases: https://github.com/graphql-java/graphql-java/releases

dependencies {
    implementation("com.graphql-java:graphql-java:$graphqlJavaVersion")
}

application {
    mainClass.set("dgroomes.App")
}
