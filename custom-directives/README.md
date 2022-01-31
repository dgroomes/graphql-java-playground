# custom-directives

NOT YET FULLY IMPLEMENTED

A GraphQL Java program that defines custom directives.

## Description

**NOTE**: This was developed on macOS and for my own personal use.

GraphQL has a neat feature called [_directives_](https://graphql.org/learn/queries/#directives). You should read the
official docs to begin your learning journey with GraphQL directives. Don't be afraid to read the GraphQL specification
itself! Section [3.13 Directives](https://spec.graphql.org/October2021/#sec-Type-System.Directives) describes directives
in clear detail.

Custom directives can be defined for use in the schema, in queries, or both!

## Instructions

Follow these instructions to build and run the app:

1. Use Java 17
2. Build the program distribution:
    * `./gradlew installDist`
3. Run the program:
    * ```bash
      build/install/custom-directives/bin/custom-directives ' 
      {
        forestAnimals(count: 3) @gp_uppercase
      }'
      ```
    * You should notice that a list of three forest animals was printed to the screen and they are all upper-cased!
4. Alias the build and run commands for happier development:
    * `alias go="./gradlew installDist && build/install/custom-directives/bin/custom-directives"`
    * For example, try the following command to build and run the program in one short step.
    * ```bash
      go ' 
      {
        forestAnimals(count: 3) @gp_uppercase
      }'
      ```
    * Next, try omitting the `@gp_uppercase` directive and see what happens.  
    * ```bash
      go ' 
      {
        forestAnimals(count: 3)
      }'
      ```    
