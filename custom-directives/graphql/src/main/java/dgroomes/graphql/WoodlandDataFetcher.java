package dgroomes.graphql;

import dgroomes.woodlands.Woodland;
import dgroomes.woodlands.WoodlandType;
import dgroomes.woodlands.Woodlands;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static dgroomes.graphql.GraphqlUtil.getNonNullArg;

/**
 * A GraphQL {@link DataFetcher} for {@link Woodland}.
 */
public class WoodlandDataFetcher implements DataFetcher<Woodland> {

    private static final Logger log = LoggerFactory.getLogger(WoodlandDataFetcher.class);

    @Override
    public Woodland get(DataFetchingEnvironment env) {
        WoodlandType type = getNonNullArg(env, "type");

        return switch (type) {
            case TROPICAL -> Woodlands.TROPICAL_RAINFOREST;
            case TEMPERATE -> Woodlands.CONIFEROUS_FOREST;
            case DESERT -> Woodlands.DESERT_WOODLAND;
        };
    }
}
