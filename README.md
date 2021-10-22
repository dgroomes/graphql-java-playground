# graphql-playground

📚 Learning and exploring _GraphQL_ and _GraphQL Java_

## Description

I've been sleeping on GraphQL for years but it has only exploded in popularity, feature richness and I think overall
goodness. I need to learn it. This project is me doing that.

This is an "echo" program that echoes a message back to you, but with some flair! Read the source code to learn more.

## Instructions

Follow these instructions to build and run the app:

1. Use Java 17
2. Build the app:
    * `./gradlew installDist`
3. Build and run the app:
    * `build/install/graphql-playground/bin/graphql-playground "hello world!"`
    * You should notice that the "hello world!" message got echoed back
4. Alias the build and run commands for happier development:
     * `alias go="./gradlew installDist && build/install/graphql-playground/bin/graphql-playground"`
     * For example, try the following command to build and run the program in one short step.
     * `go "hi there!" NORMAL`

## TODO

* DONE Write a simple GraphQL Java program that uses GraphQL Java
    * Write the GraphQL schema file
    * Write the GraphQL boilerplate code
* Example requests
    * This is an illustrative program that should be invoked from the command line. Make some example GraphQL requests
      by passing them as string arguments to the program. 

## Notes

While GraphQL is usually implemented in a web services context, there's no stopping you from using it another context
like a CLI or a desktop application.

## Reference

* [GitHub repo: "graphql-java"](https://github.com/graphql-java/graphql-java)
