package dgroomes;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static dgroomes.GraphqlUtil.getNonNullArg;

/**
 * A GraphQL {@link DataFetcher} for forest animals.
 */
public class ForestAnimalsDataFetcher implements DataFetcher<List<String>> {

    private static final Logger log = LoggerFactory.getLogger(ForestAnimalsDataFetcher.class);

    public static final List<String> FOREST_ANIMALS = List.of("Deer", "Bear", "Owl", "Squirrel");

    @Override
    public List<String> get(DataFetchingEnvironment env) {
        int count = getNonNullArg(env, "count");
        return FOREST_ANIMALS.stream().limit(count).toList();
    }
}
