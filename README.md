# graphql-playground

📚 Learning and exploring GraphQL and _GraphQL Java_ <https://www.graphql-java.com/>.

## Description

**NOTE**: This project was developed on macOS. It is for my own personal use.

I've been sleeping on GraphQL for years but it has only exploded in popularity, feature richness and I think overall
goodness. I need to learn it. This project is me doing that.

## Standalone sub-projects

This repository illustrates different concepts, patterns and examples via standalone sub-projects. Each sub-project is
completely independent of the others and do not depend on the root project. This _standalone sub-project constraint_
forces the sub-projects to be complete and maximizes the reader's chances of successfully running, understanding, and
re-using the code.

The sub-projects include:

### `sdl/`

A simple GraphQL Java program where the schema is defined in a Schema Definition Language (SDL) file.

See the README in [sdl/](sdl/).

### `programmatic/`

A simple GraphQL program where the schema is defined programmatically in Java code.

See the README in [programmatic/](programmatic/).

### `hybrid/`

An intermediate GraphQL Java program where the schema is defined by a hybrid of the SDL file and programmatically in Java code.

See the README in [hybrid/](hybrid/).

### `custom-directives/`

An advanced GraphQL Java example program that defines and implements custom directives like `@gp_uppercase` and `@gp_sort` using
a bespoke ExecutionStrategy. It also uses a web server.

See the README in [custom-directives/](custom-directives/).

### `instrumentation/`

A GraphQL Java program that defines custom instrumentation code for logging purposes.

See the README in [instrumentation/](instrumentation/).

## Reference

* [GitHub repo: "graphql-java"](https://github.com/graphql-java/graphql-java)
