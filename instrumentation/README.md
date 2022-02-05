# instrumentation

A GraphQL Java program that defines custom instrumentation code for logging purposes.

## Description

Observability is an important capability so that you can understand what your program is doing. Logs are a classic example
of observability. Do your trace-level logs shine a bright light on what the program is doing? This program instruments
the GraphQL Java software machinery with a custom implementation of `graphql.execution.instrumentation.Instrumentation`
that adds logs to the lifecycle events of the execution of a GraphQL query.

For example, the logs will show something like:


```text
beginExecution - instrumented
beginField - /stateOfH20 - instrumented
beginFieldFetch - /stateOfH20 - instrumented
instrumentDataFetcher - /stateOfH20 - instrumented
beginFieldFetch - /stateOfH20 - dispatched
beginFieldFetch - /stateOfH20 - completed
beginFieldComplete - /stateOfH20 - instrumented
beginField - /stateOfH20/description - instrumented
beginFieldFetch - /stateOfH20/description - instrumented
instrumentDataFetcher - /stateOfH20/description - instrumented
beginFieldFetch - /stateOfH20/description - dispatched
beginFieldFetch - /stateOfH20/description - completed
beginFieldComplete - /stateOfH20/description - instrumented
beginFieldComplete - /stateOfH20/description - dispatched
beginFieldComplete - /stateOfH20/description - completed
beginField - /stateOfH20/description - dispatched
beginField - /stateOfH20/description - completed
beginField - /stateOfH20/nextState - instrumented
beginFieldFetch - /stateOfH20/nextState - instrumented
instrumentDataFetcher - /stateOfH20/nextState - instrumented
beginFieldFetch - /stateOfH20/nextState - dispatched
beginFieldFetch - /stateOfH20/nextState - completed
beginFieldComplete - /stateOfH20/nextState - instrumented
beginField - /stateOfH20/nextState/description - instrumented
beginFieldFetch - /stateOfH20/nextState/description - instrumented
instrumentDataFetcher - /stateOfH20/nextState/description - instrumented
beginFieldFetch - /stateOfH20/nextState/description - dispatched
beginFieldFetch - /stateOfH20/nextState/description - completed
beginFieldComplete - /stateOfH20/nextState/description - instrumented
beginFieldComplete - /stateOfH20/nextState/description - dispatched
beginFieldComplete - /stateOfH20/nextState/description - completed
beginField - /stateOfH20/nextState/description - dispatched
beginField - /stateOfH20/nextState/description - completed
beginFieldComplete - /stateOfH20/nextState - dispatched
beginFieldComplete - /stateOfH20/nextState - completed
beginField - /stateOfH20/nextState - dispatched
beginField - /stateOfH20/nextState - completed
beginFieldComplete - /stateOfH20 - dispatched
beginFieldComplete - /stateOfH20 - completed
beginField - /stateOfH20 - dispatched
beginField - /stateOfH20 - completed
beginExecution - completed
instrumentExecutionResult - instrumented
instrumentExecutionResult - completed

dgroomes.Cli - Results:
dgroomes.graphql.GraphqlUtil - {
  "stateOfH20" : {
    "description" : "Frozen water is ice (the solid state)",
    "nextState" : {
      "description" : "Water (the liquid state)"
    }
  }
}
```

## Instructions

Follow these instructions to build and run the app:

1. Use Java 17
2. Build the program distribution:
    * `./gradlew :installDist`
3. Run the program:
    * ```bash
      build/install/instrumentation/bin/instrumentation ' 
      {
        stateOfH20 {
          description
        }
      }'
      ```
4. Alias the build and run commands for happier development:
    * `alias go="./gradlew :installDist && build/install/instrumentation/bin/instrumentation"`
    * For example, try the following command to build and run the program in one short step.
    * ```bash
      go ' 
      {
        stateOfH20 {
          description
          nextState {
            description
          }
        }
      }'
      ```
    * Next, get the highest state of H20.
    * ```bash
      go ' 
      {
        stateOfH20 {
          description
          nextState {
            description
            nextState {
              description
            }
          }
        }
      }'
      ```

Tip: to start the program in Java's debug mode, set the following environment variable:
* `export INSTRUMENTATION_OPTS=-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=*:5005`
