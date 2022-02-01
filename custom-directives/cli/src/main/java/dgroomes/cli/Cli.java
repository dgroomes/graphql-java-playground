package dgroomes.cli;

import dgroomes.graphql.GpDirectivesExecutionStrategy;
import dgroomes.graphql.GraphqlUtil;
import dgroomes.graphql.SortOrder;
import dgroomes.woodlands.WoodlandType;
import graphql.GraphQL;
import graphql.execution.ExecutionStrategy;
import graphql.schema.idl.NaturalEnumValuesProvider;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static graphql.schema.idl.RuntimeWiring.newRuntimeWiring;

/**
 * A toy Java program that uses GraphQL to fetch information about woodlands their animals.
 */
public class Cli {

    private static final Logger log = LoggerFactory.getLogger(Cli.class);

    public static void main(String[] args) {
        if (args.length != 1) {
            log.error("Expected exactly one argument but found {}", args.length);
            System.exit(1);
        }

        var graphqlQuery = args[0];

        var schemaParser = new SchemaParser();
        var typeDefinitionRegistry = schemaParser.parse(new File("schema.graphqls"));

        var runtimeWiring = newRuntimeWiring()
                .type("Query", builder -> builder.dataFetcher("woodland", new WoodlandDataFetcher()))
                .type("SortOrder", builder -> builder.enumValues(new NaturalEnumValuesProvider<>(SortOrder.class)))
                .type("WoodlandType", builder -> builder.enumValues(new NaturalEnumValuesProvider<>(WoodlandType.class)))
                .build();

        var schemaGenerator = new SchemaGenerator();
        var graphQLSchema = schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);

        // Create a custom execution strategy that incorporates some custom directives like "@gp_uppercase"
        ExecutionStrategy queryExecutionStrategy = new GpDirectivesExecutionStrategy();

        var build = GraphQL.newGraphQL(graphQLSchema)
                .queryExecutionStrategy(queryExecutionStrategy)
                .build();
        var executionResult = build.execute(graphqlQuery);

        GraphqlUtil.printResult(executionResult);
    }
}
