package dgroomes.graphql;

import graphql.ExecutionResult;
import graphql.execution.AbortExecutionException;
import graphql.execution.instrumentation.InstrumentationContext;
import graphql.execution.instrumentation.InstrumentationState;
import graphql.execution.instrumentation.SimplePerformantInstrumentation;
import graphql.execution.instrumentation.parameters.InstrumentationExecutionParameters;
import graphql.execution.instrumentation.parameters.InstrumentationFieldParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

public class EarlyExitInstrumentation extends SimplePerformantInstrumentation {

    private static final Logger log = LoggerFactory.getLogger("query-execution");

    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public InstrumentationContext<ExecutionResult> beginExecution(InstrumentationExecutionParameters parameters, InstrumentationState state) {
        counter.set(0);
        return super.beginExecution(parameters, state);
    }

    @Override
    public InstrumentationContext<ExecutionResult> beginField(InstrumentationFieldParameters parameters, InstrumentationState state) {
        if (parameters.getField().getName().equals("description")) {
            var x = counter.incrementAndGet();
            log.info("'description' field encountered {} times", x);
            if (x >= 3) {
                throw new AbortExecutionException("Aborting execution on the third 'description' field. How does this affect the final result?");
            }
        }
        return super.beginField(parameters, state);
    }
}
