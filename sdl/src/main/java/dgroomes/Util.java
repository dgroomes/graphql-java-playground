package dgroomes;

import graphql.schema.DataFetchingEnvironment;

import static java.util.Objects.requireNonNull;

public class Util {

    public static <T> T getNonNullArg(DataFetchingEnvironment env, String argName) {
        T argument = env.getArgument(argName);
        return requireNonNull(argument, "Argument '%s' was null but must be non-null".formatted(argName));
    }
}
