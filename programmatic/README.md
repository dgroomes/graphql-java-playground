# programmatic

A simple GraphQL program where the schema is defined programmatically in Java code.

## Instructions

Follow these instructions to build and run the app:

1. Use Java 17
2. Build the app:
    * `./gradlew installDist`
3. Build and run the app:
    * ```
      build/install/programmatic/bin/programmatic ' 
      {
        localTime(timezone: AMERICA_CHICAGO)
      }'
      ```
    * You should notice that the local time in Chicago was printed to the terminal.
4. Alias the build and run commands for happier development:
    * `alias go="./gradlew installDist && build/install/programmatic/bin/programmatic"`
    * For example, try the following command to build and run the program in one short step.
    * ```
      go ' 
      {
        localTime(timezone: AMERICA_CHICAGO)
      }'
      ```
    * Next, try this.
    * ```
      go '
      {
        localTime(timezone: EUR
      }'
      ```

## TODO

* DONE Create a data fetcher that gets the local time of the request time zone. Support a few time zones.
* DONE Replace the SDL schema file with a programmatic definition.
* DONE Is it possible to list all known IANA timezones in Java? If so, then do that and then programmaticaly define the
  GraphQL TimeZone enum.
