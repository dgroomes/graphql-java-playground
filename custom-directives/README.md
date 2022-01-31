# custom-directives

An advanced GraphQL Java program that defines and implements custom directives like `@gp_uppercase` using a bespoke ExecutionStrategy.

## Description

**NOTE**: This was developed on macOS and for my own personal use.

GraphQL has a neat feature called [_directives_](https://graphql.org/learn/queries/#directives). You should read the
official docs to begin your learning journey with GraphQL directives. Don't be afraid to read the GraphQL specification
itself! Section [3.13 Directives](https://spec.graphql.org/October2021/#sec-Type-System.Directives) describes directives
in clear detail.

Custom directives can be defined for use in the schema, in queries, or both! In this project, we define a directive that
can be used in queries for upper-casing strings: `@gp_uppercase`. Per convention, the directive is given a custom prefix
ending in `_` so that users can distinguish it from standard built-in directives like `@skip` and `@include`. In this
case, `gp` is the acronym of `graphql-playground.`

## Instructions

Follow these instructions to build and run the app:

1. Use Java 17
2. Build the program distribution:
    * `./gradlew installDist`
3. Run the program:
    * ```bash
      build/install/custom-directives/bin/custom-directives ' 
      {
        forest(animalsCount: 3) {
          type
          animals @gp_uppercase
        }
      }'
      ```
    * You should notice that a list of three forest animals was printed to the screen and they are all upper-cased! It
      should look something like the following.
    * ```text
      16:08:54 [main] INFO dgroomes.graphql.GraphqlUtil - {forest={type=Coniferous, animals=[DEER, BEAR, OWL]}}
      ```
4. Alias the build and run commands for happier development:
    * `alias go="./gradlew installDist && build/install/custom-directives/bin/custom-directives"`
    * For example, try the following command to build and run the program in one short step.
    * ```bash
      go ' 
      {
        forest(animalsCount: 4) {
          type
          animals @gp_uppercase
        }
      }'
      ```
    * Next, try omitting the `@gp_uppercase` directive and see what happens.  
    * ```bash
      go ' 
      {
        forest(animalsCount: 3) {
          type
          animals
        }
      }'
      ```    


Tip: to start the program in Java's debug mode, set the following environment variable:
* `export CUSTOM_DIRECTIVES_OPTS=-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=*:5005`

## WishList

General clean-ups, TODOs and things I wish to implement for this project:

* DONE Genericize the implementation of `@gp_uppercase`. Can it be done somewhere else in the GraphQL Java machinery? I
  suppose it could be done in a base class that extends DataFetchers, but that's not great. It could be done in GraphQL's
  instrumentation layer, but that's quite low-level, and designed for metrics. Is there somewhere else? I can't find
  anything in the GraphQL Java docs for query directives, only [schema directives](https://www.graphql-java.com/documentation/sdl-directives).
  So maybe this isn't supported in a "paved road" way, but this library is so extensible I think there's a way.
  * Update: I think instrumentation is the way to go. The type hierarchy of GraphQL's "execution machinery" is thick and
    complicated. It is a true framework. I'll say it again, GraphQL Java is a framework, not a library! Looking at the
    code in the classes `graphql.GraphQL`, `graphql.execution.AsyncExecutionStrategy`, and `graphql.execution.Execution`
    make me realize the framework qualities of GraphQL Java. That said, I think it's really well done and I could
    probably digest and understand the codebase without too much of a hassle, but still, it is circuitous so I'll pass
    for now. Instead I'll focus on the public interfaces, like `graphql.execution.instrumentation.Instrumentation` which
    the authors of the framework intend for users to use directly.
  * Update 2: Now I've changed my mind again and think extending the `ExecutionStrategy` is the right move. It has
    protected methods `graphql.execution.ExecutionStrategy.completeValue` and `graphql.execution.ExecutionStrategy.completeValueForList`
    which return a `graphql.execution.FieldValueInfo` instance which is marked as `@PublicApi`. The combination of `protected`
    methods and a public API class are an indicator that this is a blessed extension point in the framework.
