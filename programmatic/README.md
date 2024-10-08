# programmatic

A simple GraphQL program where the schema is defined programmatically in Java code.


## Instructions

Follow these instructions to build and run the app:

1. Pre-requisite: Java 21
2. Build the app:
    * ```bash
      ./gradlew installDist
      ```
3. Build and run the app:
    * ```bash
      build/install/programmatic/bin/programmatic ' 
      {
        localTime(timezone: AMERICA_CHICAGO)
      }'
      ```
    * You should notice that the local time in Chicago was printed to the terminal.
4. Alias the build and run commands for happier development:
    * ```bash
      alias go="./gradlew installDist && build/install/programmatic/bin/programmatic"
      ```
    * For example, try the following command to build and run the program in one short step.
    * ```bash
      go ' 
      {
        localTime(timezone: AMERICA_CHICAGO)
      }'
      ```
    * Next, try a different timezone.
    * ```bash
      go '
      {
        localTime(timezone: EUROPE_PARIS)
      }'
      ```


## TODO

* [x] DONE Create a data fetcher that gets the local time of the request time zone. Support a few time zones.
* [x] DONE Replace the SDL schema file with a programmatic definition.
* [x] DONE Is it possible to list all known IANA timezones in Java? If so, then do that and then programmaticaly define the
  GraphQL TimeZone enum.
