package dgroomes;

import graphql.GraphQL;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static graphql.schema.idl.RuntimeWiring.newRuntimeWiring;

public class App {

    private static final Logger log = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        log.info("Received the following arguments: {}", String.join(", ", args));

        if (args.length != 2) {
            log.error("Expected exactly two arguments but found {}", args.length);
            System.exit(1);
        }

        var message = args[0];
        var echoFlavor = args[1];

        var schemaParser = new SchemaParser();
        var typeDefinitionRegistry = schemaParser.parse(new File("schema.graphqls"));

        var runtimeWiring = newRuntimeWiring()
                .type("Query", builder -> builder.dataFetcher("echo", new EchoDataFetcher()))
                .build();

        var schemaGenerator = new SchemaGenerator();
        var graphQLSchema = schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);

        var build = GraphQL.newGraphQL(graphQLSchema).build();
        var executionResult = build.execute("""
                {
                    echo(message: "%s", echoFlavor: %s)
                }
                """.formatted(message, echoFlavor));

        log.info(executionResult.getData().toString());
    }
}
