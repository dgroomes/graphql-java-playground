package dgroomes;

import dgroomes.graphql.ExecutionResultGpImpl;
import graphql.ExecutionResult;
import graphql.execution.AsyncExecutionStrategy;
import graphql.execution.ExecutionContext;
import graphql.execution.ExecutionStrategyParameters;
import graphql.language.Directive;
import graphql.schema.GraphQLScalarType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * This is an implementation of {@link graphql.execution.ExecutionStrategy} that implements the graphql-playground
 * custom directives like "@gp_uppercase".
 * <p>
 * It doesn't extend from {@link graphql.execution.ExecutionStrategy} directly but instead extends from {@link AsyncExecutionStrategy}
 * because {@link AsyncExecutionStrategy} already contains a good implementation for the abstract "execute(...)" method.
 * <p>
 * Note: if you wanted to include graphql-playground directives and other custom directives, then this class doesn't
 * really accommodate that design. You would need to invent a better "composition over inheritance" design. But for the
 * purposes of an illustrative repo like "graphql-playground", that goes beyond the scope of the illustration.
 */
public class GpDirectivesExecutionStrategy extends AsyncExecutionStrategy {

    private static final Logger log = LoggerFactory.getLogger(GpDirectivesExecutionStrategy.class);

    /**
     * The {@link graphql.execution.ExecutionStrategy} base class provides lifecycle hooks where we can extend with
     * custom code. In this case, the 'completeValueForScalar' method is where we can hook into the execution and
     * transform the result before it is returned.
     */
    @Override
    protected CompletableFuture<ExecutionResult> completeValueForScalar(ExecutionContext executionContext, ExecutionStrategyParameters parameters, GraphQLScalarType scalarType, Object result) {
        CompletableFuture<ExecutionResult> future = super.completeValueForScalar(executionContext, parameters, scalarType, result);

        return handleUppercase(future, parameters);
    }

    /**
     * Uppercase the value if its field is annotated with the "@gp_uppercase" directive. This is a bit complicated
     * because I'm not sure what all edge cases I have to consider. For example, is the CompletableFuture done
     * resolving? If we are at this point in the code, and the field is a scalar, then I'm pretty sure the future should
     * always be done at this point, but I'm not sure.
     */
    private CompletableFuture<ExecutionResult> handleUppercase(CompletableFuture<ExecutionResult> future, ExecutionStrategyParameters parameters) {
        // Find any directives that may annotate the query field
        List<Directive> directives = parameters.getField().getSingleField().getDirectives();

        var shouldUppercase = directives.stream().anyMatch(directive -> directive.getName().equals("gp_uppercase"));
        if (!shouldUppercase) return future;
        log.trace("This value will be uppercased because its field was annotated with the '@gp_uppercase' directive");

        var isDone = future.isDone();
        if (!isDone) {
            log.warn("The '@gp_uppercase' directive cannot be applied to a field that is not yet resolved. This is an unexpected state.");
            return future;
        }

        ExecutionResult executionResult;
        try {
            executionResult = future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new IllegalStateException("Error while getting the result of the FieldValueInfo's fieldValue CompletableFuture. This is unexpected because the CompletableFuture is already known to be done.");
        }

        if (!executionResult.isDataPresent()) {
            return future;
        }

        if (!(executionResult.getData() instanceof String stringData)) {
            log.warn("This field's data is not a String but this field was annotated with the '@gp_uppercase' directive. This is not expected. Found type {}", executionResult.getData().getClass());
            return future;
        }

        String upperCase = stringData.toUpperCase();
        return CompletableFuture.completedFuture(new ExecutionResultGpImpl(upperCase));
    }
}
