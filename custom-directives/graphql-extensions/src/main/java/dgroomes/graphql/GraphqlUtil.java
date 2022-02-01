package dgroomes.graphql;

import graphql.ExecutionResult;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Objects.requireNonNull;

public class GraphqlUtil {

    private static final Logger log = LoggerFactory.getLogger(GraphqlUtil.class);

    /**
     * Get an argument from the GraphQL {@link DataFetchingEnvironment}. This argument is expected to be non-null and if
     * it is null a runtime exception is thrown.
     */
    public static <T> T getNonNullArg(DataFetchingEnvironment env, String argName) {
        T argument = env.getArgument(argName);
        return requireNonNull(argument, "Argument '%s' was null but must be non-null".formatted(argName));
    }

    /**
     * Print the contents of an {@link ExecutionResult}. If there are errors, print the errors.
     */
    public static void printResult(ExecutionResult executionResult) {
        if (executionResult.isDataPresent()) {
            log.info(executionResult.getData().toString());
        } else {
            executionResult.getErrors()
                    .stream()
                    .map(Object::toString)
                    .forEach(log::error);
        }
    }
}
