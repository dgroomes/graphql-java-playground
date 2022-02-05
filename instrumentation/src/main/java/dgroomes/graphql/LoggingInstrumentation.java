package dgroomes.graphql;

import graphql.ExecutionResult;
import graphql.execution.ResultPath;
import graphql.execution.instrumentation.InstrumentationContext;
import graphql.execution.instrumentation.SimpleInstrumentation;
import graphql.execution.instrumentation.parameters.InstrumentationExecutionParameters;
import graphql.execution.instrumentation.parameters.InstrumentationFieldCompleteParameters;
import graphql.execution.instrumentation.parameters.InstrumentationFieldFetchParameters;
import graphql.execution.instrumentation.parameters.InstrumentationFieldParameters;
import graphql.schema.DataFetcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * Adds logging to lifecycle events during the execution of a GraphQL query.
 *
 * Note: This code is a bit circuitous because I was trying to be DRY.
 */
public class LoggingInstrumentation extends SimpleInstrumentation {

    private static final Logger log = LoggerFactory.getLogger("query-execution");

    /**
     * A common trace method that can be used for instrumentation lifecycle hooks the return {@link InstrumentationContext}
     * @param doLog a log function
     */
    private <T> InstrumentationContext<T> traceInstrumentationContext(Consumer<String> doLog) {
        doLog.accept("instrumented");

        return new InstrumentationContext<>() {
            @Override
            public void onDispatched(CompletableFuture<T> result) {
                doLog.accept("dispatched");
            }

            @Override
            public void onCompleted(T result, Throwable t) {
                doLog.accept("completed");
            }
        };
    }

    @Override
    public InstrumentationContext<ExecutionResult> beginExecution(InstrumentationExecutionParameters parameters) {
        return traceInstrumentationContext(event -> log.trace("{} - {}", "beginExecution", event));
    }

    @Override
    public InstrumentationContext<ExecutionResult> beginField(InstrumentationFieldParameters parameters) {
        ResultPath path = parameters.getExecutionStepInfo().getPath();
        Consumer<String> doLog = event -> log.trace("{} - {} - {}", "beginField", path, event);
        return traceInstrumentationContext(doLog);
    }

    @Override
    public InstrumentationContext<Object> beginFieldFetch(InstrumentationFieldFetchParameters parameters) {
        ResultPath path = parameters.getExecutionStepInfo().getPath();
        Consumer<String> doLog = event -> log.trace("{} - {} - {}", "beginFieldFetch", path, event);
        return traceInstrumentationContext(doLog);
    }

    @Override
    public InstrumentationContext<ExecutionResult> beginFieldComplete(InstrumentationFieldCompleteParameters parameters) {
        ResultPath path = parameters.getExecutionStepInfo().getPath();
        Consumer<String> doLog = event -> log.trace("{} - {} - {}", "beginFieldComplete", path, event);
        return traceInstrumentationContext(doLog);
    }

    @Override
    public DataFetcher<?> instrumentDataFetcher(DataFetcher<?> dataFetcher, InstrumentationFieldFetchParameters parameters) {
        ResultPath path = parameters.getEnvironment().getExecutionStepInfo().getPath();
        log.trace("{} - {} - {}", "instrumentDataFetcher", path, "instrumented");
        return super.instrumentDataFetcher(dataFetcher, parameters);
    }

    @Override
    public CompletableFuture<ExecutionResult> instrumentExecutionResult(ExecutionResult executionResult, InstrumentationExecutionParameters parameters) {
        Consumer<String> doLog = event -> log.trace("{} - {}", "instrumentExecutionResult", event);
        doLog.accept("instrumented");

        return super.instrumentExecutionResult(executionResult, parameters)
                .whenComplete((executionResult1, throwable) -> {
                    if (throwable != null) {
                        doLog.accept("error");
                    }
                    doLog.accept("completed");
                });
    }
}
