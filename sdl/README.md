# sdl

A simple GraphQL Java program where the schema is defined in a Schema Definition Language (SDL) file.


## Description

This is an "echo" program that echoes a message back to you, but with some flair! Read the source code to learn more.


## Instructions

Follow these instructions to build and run the app:

1. Use Java 17
2. Build the app:
    * ```bash
      ./gradlew installDist
      ```
3. Build and run the app:
    * ```bash
      build/install/sdl/bin/sdl ' 
      {
        echo(message: "hello world!", echoFlavor: LOUD)
      }'
      ```
    * You should notice that the "hello world!" message got echoed back in a loud way!
4. Alias the build and run commands for happier development:
    * ```bash
      alias go="./gradlew installDist && build/install/sdl/bin/sdl"
      ```
    * For example, try the following command to build and run the program in one short step.
    * ```bash
      go ' 
      {
        echo(message: "hello world!", echoFlavor: LOUD)
      }'
      ```
    * Next, try the `NORMAL` echo flavor.  
    * ```bash
      go '
      {
        echo(message: "anyone there?", echoFlavor: NORMAL)
      }'
      ```    


## TODO

* [x] DONE Write a simple GraphQL Java program that uses GraphQL Java
    * Write the GraphQL schema file
    * Write the GraphQL boilerplate code
* [x] DONE Example requests
    * This is an illustrative program that should be invoked from the command line. Make some example GraphQL requests
      by passing them as string arguments to the program.


## Notes

While GraphQL is usually implemented in a web services context, there's no stopping you from using it another context
like a CLI or a desktop application.
