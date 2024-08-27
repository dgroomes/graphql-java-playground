# hybrid

An intermediate GraphQL Java program where the schema is defined by a hybrid of the SDL file and programmatically in Java code.


## Instructions

Follow these instructions to build and run the program.

1. Pre-requisite: Java 21
2. Build the program distribution:
    * ```bash
      ./gradlew :cli:installDist
      ```
3. Run the program:
    * ```bash
      cli/build/install/cli/bin/cli ' 
      {
        javaInfo(component: VERSION)
      }'
      ```
    * It should print something like this:
      ```text
      23:24:52 [main] INFO dgroomes.graphql.GraphqlUtil - {javaInfo=The Java version is: 21.0.3}
      ```
4. Alias the build and run commands for happier development:
    * ```bash
      alias go="./gradlew :cli:installDist && cli/build/install/cli/bin/cli"
      ```
    * For example, try the following command to build and run the program in one short step.
    * ```bash
      go ' 
      {
        javaInfo(component: RUNTIME_MODE)
      }'
      ```
5. Try one of the dynamic Java components:
    * ```bash
      go '
      {
        javaInfo(component: PACKAGE_ORG_SLF4J)
      }'
      ```
    * It should print out all the classes in the `org.slf4j` package. The `PACKAGE_ORG_SLF4J` enum value was defined
      programmatically at runtime. In other words, `PACKAGE_ORG_SLF4J` is not defined in the SDL file `schema.graphqls`!
      The ability to define the GraphQL schema partly in a static SDL file and partly at runtime showcases GraphQL and
      `graphql-java`'s flexibility. Pretty neat! The response will look something like the following.
    * ```text
      18:06:59 [main] INFO dgroomes.graphql.GraphqlUtil - {javaInfo=org.slf4j.ILoggerFactory
      org.slf4j.IMarkerFactory
      org.slf4j.Logger
      org.slf4j.LoggerFactory
      org.slf4j.MDC
      org.slf4j.MDC$MDCCloseable
      ... omitted...
      ```
