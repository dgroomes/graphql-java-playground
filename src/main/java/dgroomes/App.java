package dgroomes;

import graphql.GraphQL;
import graphql.schema.StaticDataFetcher;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static graphql.schema.idl.RuntimeWiring.newRuntimeWiring;

public class App {

    private static final Logger log = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        log.info("Received the following arguments: {}", String.join(", ", args));

        if (args.length != 1) {
            log.error("Expected exactly one argument but found {}", args.length);
            System.exit(1);
        }

        var schema = """
                type Query{
                    message: String
                }
                """;

        var schemaParser = new SchemaParser();
        var typeDefinitionRegistry = schemaParser.parse(schema);

        var runtimeWiring = newRuntimeWiring()
                .type("Query", builder -> builder.dataFetcher("message", new StaticDataFetcher(args[0])))
                .build();

        var schemaGenerator = new SchemaGenerator();
        var graphQLSchema = schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);

        var build = GraphQL.newGraphQL(graphQLSchema).build();
        var executionResult = build.execute("""
                {
                    message
                }
                """);

        log.info(executionResult.getData().toString());
    }
}
