package dgroomes.graphql;


import graphql.ExecutionResult;
import graphql.GraphQLError;
import graphql.Internal;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * GraphQL Java has exactly one implementation of {@link ExecutionResult}: {@link graphql.ExecutionResultImpl}. This
 * class is marked as {@link Internal}, so we will not use and instead create a new implementation. It is very similar.
 */
public record ExecutionResultGpImpl(
        List<GraphQLError> errors,
        Object data,
        Map<Object, Object> extensions) implements ExecutionResult {


    public ExecutionResultGpImpl {
        if (errors != null) errors = List.copyOf(errors);
    }

    public ExecutionResultGpImpl(Object data) {
        this(null, data, null);
    }

    @Override
    public boolean isDataPresent() {
        return data != null;
    }

    @Override
    public List<GraphQLError> getErrors() {
        return errors;
    }

    @Override
    @SuppressWarnings("TypeParameterUnusedInFormals")
    public <T> T getData() {
        //noinspection unchecked
        return (T) data;
    }

    @Override
    public Map<Object, Object> getExtensions() {
        return extensions;
    }

    @Override
    public Map<String, Object> toSpecification() {
        var result = new LinkedHashMap<String, Object>();

        if (errors != null && !errors.isEmpty()) result.put("errors", errorsToSpec(errors));
        if (isDataPresent()) result.put("data", data);
        if (extensions != null) result.put("extensions", extensions);

        return result;
    }

    private List<?> errorsToSpec(List<GraphQLError> errors) {
        return errors.stream().map(GraphQLError::toSpecification).toList();
    }
}
