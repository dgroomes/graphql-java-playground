package dgroomes.graphql;

import graphql.ExecutionResult;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

import static java.util.Objects.requireNonNull;

public class GraphqlUtil {

    private static final Logger log = LoggerFactory.getLogger(GraphqlUtil.class);

    /**
     * Print the contents of an {@link ExecutionResult}. If there are errors, print the errors.
     * @param executionResult serialize this to a string and print it
     * @param serializer (optional) the executionResult will be serialized using this function. You might want to use
     *                   JSON. If not value is given, then "toString" will be used.
     */
    public static void printResult(ExecutionResult executionResult, Function<Object, String> serializer) {
        if (serializer == null) {
            serializer = Object::toString;
        }

        if (executionResult.isDataPresent()) {
            log.info(serializer.apply(executionResult.getData()));
        } else {
            executionResult.getErrors()
                    .stream()
                    .map(serializer)
                    .forEach(log::error);
        }
    }

}
