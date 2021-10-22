package dgroomes;

import graphql.GraphQL;
import graphql.schema.GraphQLEnumType;
import graphql.schema.GraphQLSchemaElement;
import graphql.schema.GraphQLTypeVisitorStub;
import graphql.schema.SchemaTransformer;
import graphql.schema.idl.NaturalEnumValuesProvider;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.util.TraversalControl;
import graphql.util.TraverserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static graphql.schema.GraphQLEnumType.newEnum;
import static graphql.schema.idl.RuntimeWiring.newRuntimeWiring;

/**
 * A toy Java program that uses GraphQL to echo a message back to the user.
 */
public class EchoGraphql {

    private static final Logger log = LoggerFactory.getLogger(EchoGraphql.class);

    public static void main(String[] args) {
        if (args.length != 1) {
            log.error("Expected exactly one argument but found {}", args.length);
            System.exit(1);
        }

        var graphqlQuery = args[0];

        var schemaParser = new SchemaParser();
        var typeDefinitionRegistry = schemaParser.parse(new File("schema.graphqls"));

        var runtimeWiring = newRuntimeWiring()
                .type("Query", builder -> builder.dataFetcher("echo", new EchoDataFetcher()))
                .type("EchoFlavor", builder -> builder.enumValues(new NaturalEnumValuesProvider<>(EchoFlavor.class)))
                .build();

        var schemaGenerator = new SchemaGenerator();
        var graphQLSchema = schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
        // Transform the schema after it was generated from the SDL. This is useful when the schema needs to contain
        // dynamic content that can't realistically be saved in the SDL file, which is a static document.
        //
        // Specifically, add an "EXTRA" value to the EchoFlavor enum. This is a contrived example. A real example would
        // add something more complicated.
        graphQLSchema = SchemaTransformer.transformSchema(graphQLSchema, new GraphQLTypeVisitorStub() {
            @Override
            public TraversalControl visitGraphQLEnumType(GraphQLEnumType node, TraverserContext<GraphQLSchemaElement> context) {
                if (node.getName().equals("EchoFlavor")) {
                    var newNode = newEnum(node);
                    newNode.value("EXTRA");
                    return changeNode(context, newNode.build());
                } else {
                    return super.visitGraphQLEnumType(node, context);
                }
            }
        });

        var build = GraphQL.newGraphQL(graphQLSchema).build();
        var executionResult = build.execute(graphqlQuery);

        log.info(executionResult.getData().toString());
    }
}
