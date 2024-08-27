package dgroomes;

import dgroomes.graphql.EarlyExitInstrumentation;
import dgroomes.graphql.GraphqlUtil;
import dgroomes.graphql.LoggingInstrumentation;
import dgroomes.json.JsonUtil;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static graphql.schema.idl.RuntimeWiring.newRuntimeWiring;

/**
 * A toy Java program that uses GraphQL in a weird way. And to help understand the weirdness, special instrumentation
 * code is wired into GraphQL which logs the state of the program as it executes. This is an example of observability.
 */
public class Cli {

    private static final Logger log = LoggerFactory.getLogger(Cli.class);

    public static void main(String[] args) {
        if (args.length != 1) {
            log.error("Expected exactly one argument but found {}", args.length);
            System.exit(1);
        }

        var graphqlQuery = args[0];

        graphql.GraphQL graphQL = getGraphQL();
        var executionResult = graphQL.execute(graphqlQuery);

        log.info("Results:");
        GraphqlUtil.printResult(executionResult, JsonUtil::toJson);
    }

    /**
     * Create the GraphQL object.
     * <p>
     * This creates the schema, wires in the instrumentation, etc.
     */
    public static graphql.GraphQL getGraphQL() {
        GraphQLSchema graphQLSchema = schema();

        return graphql.GraphQL.newGraphQL(graphQLSchema)
                .instrumentation(new LoggingInstrumentation())
                .instrumentation(new EarlyExitInstrumentation())
                .build();
    }

    /**
     * Create the GraphQL schema
     */
    private static GraphQLSchema schema() {
        var schemaParser = new SchemaParser();
        var typeRegistry = new TypeDefinitionRegistry();
        typeRegistry.merge(schemaParser.parse(new File("schema.graphqls")));

        var runtimeWiring = newRuntimeWiring()
                .type("Query", builder -> builder.dataFetcher("stateOfH20", new StateOfH20DataFetcher()))
                .build();

        var schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
    }
}
