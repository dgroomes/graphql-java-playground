plugins {
    application
}

repositories {
    mavenCentral()
}

val slf4jVersion = "1.7.32" // releases: http://www.slf4j.org/news.html
val graphqlJavaVersion = "17.3" // releases: https://github.com/graphql-java/graphql-java/releases

dependencies {
    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    runtimeOnly("org.slf4j:slf4j-simple:$slf4jVersion")

    implementation("com.graphql-java:graphql-java:$graphqlJavaVersion")
}

application {
    mainClass.set("dgroomes.LocalTimeGraphql")
}
