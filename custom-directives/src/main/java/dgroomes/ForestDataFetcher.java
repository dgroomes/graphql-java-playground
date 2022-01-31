package dgroomes;

import dgroomes.forest.Forest;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static dgroomes.graphql.GraphqlUtil.getNonNullArg;

/**
 * A GraphQL {@link DataFetcher} for forests.
 */
public class ForestDataFetcher implements DataFetcher<Forest> {

    private static final Logger log = LoggerFactory.getLogger(ForestDataFetcher.class);

    public static final List<String> FOREST_ANIMALS = List.of("Deer", "Bear", "Owl", "Squirrel");

    @Override
    public Forest get(DataFetchingEnvironment env) {
        int animalsCount = getNonNullArg(env, "animalsCount");
        var animals = FOREST_ANIMALS.stream().limit(animalsCount).toList();
        return new Forest("Coniferous", animals);
    }
}
