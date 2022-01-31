# graphql-playground

📚 Learning and exploring _GraphQL_ and _GraphQL Java_

## Description

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

An advanced GraphQL Java program that defines and implements custom directives like `@gp_uppercase` using a bespoke ExecutionStrategy.

See the README in [custom-directives/](custom-directives/).

## Reference

* [GitHub repo: "graphql-java"](https://github.com/graphql-java/graphql-java)
