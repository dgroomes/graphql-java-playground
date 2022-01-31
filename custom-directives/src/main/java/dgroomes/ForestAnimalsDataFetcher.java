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

        // If the "@gp_uppercase" directive is present, then the animal names should be uppercased.
        // Note: it would be better if the uppercasing code was implemented generically, outside of this DataFetcher.
        // The way it is now, this code would have to re-implemented for all future DataFetchers in the codebase. It
        // would be easy to forget to implement this.
        var shouldUppercase = !env.getQueryDirectives().getImmediateDirective("gp_uppercase").isEmpty();

        var forestAnimals = FOREST_ANIMALS.stream().limit(count);
        if (shouldUppercase) {
            forestAnimals = forestAnimals.map(String::toUpperCase);
        }
        return forestAnimals.toList();
    }
}
