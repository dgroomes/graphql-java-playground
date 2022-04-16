package dgroomes.graphql;

import graphql.schema.DataFetchingEnvironment;

import static java.util.Objects.requireNonNull;

public class GraphqlUtil {

  /**
   * Get an argument from the GraphQL {@link DataFetchingEnvironment}. This argument is expected to be non-null and if
   * it is null a runtime exception is thrown.
   */
  public static <T> T getNonNullArg(DataFetchingEnvironment env, String argName) {
    T argument = env.getArgument(argName);
    return requireNonNull(argument, "Argument '%s' was null but must be non-null".formatted(argName));
  }
}
