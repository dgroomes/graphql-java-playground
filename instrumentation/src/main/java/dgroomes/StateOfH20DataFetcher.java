package dgroomes;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A GraphQL {@link DataFetcher} for {@link StateOfH20}.
 */
public class StateOfH20DataFetcher implements DataFetcher<StateOfH20> {

    private static final Logger log = LoggerFactory.getLogger(StateOfH20DataFetcher.class);

    @Override
    public StateOfH20 get(DataFetchingEnvironment env) {
        return StateOfH20.ICE;
    }
}
