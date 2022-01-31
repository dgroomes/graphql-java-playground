package dgroomes;

import graphql.GraphQL;
import graphql.execution.ExecutionStrategy;
import graphql.schema.GraphQLDirective;
import graphql.schema.GraphQLSchemaElement;
import graphql.schema.GraphQLTypeVisitorStub;
import graphql.schema.SchemaTransformer;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.util.TraversalControl;
import graphql.util.TraverserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static graphql.introspection.Introspection.DirectiveLocation.FIELD;
import static graphql.schema.idl.RuntimeWiring.newRuntimeWiring;

/**
 * A toy Java program that uses GraphQL to list some forest animals.
 */
public class CustomDirectives {

    private static final Logger log = LoggerFactory.getLogger(CustomDirectives.class);

    public static void main(String[] args) {
        if (args.length != 1) {
            log.error("Expected exactly one argument but found {}", args.length);
            System.exit(1);
        }

        var graphqlQuery = args[0];

        var schemaParser = new SchemaParser();
        var typeDefinitionRegistry = schemaParser.parse(new File("schema.graphqls"));

        var runtimeWiring = newRuntimeWiring()
                .type("Query", builder -> builder.dataFetcher("forestAnimals", new ForestAnimalsDataFetcher()))
                .build();

        var schemaGenerator = new SchemaGenerator();
        var graphQLSchema = schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);

        // Further transform the schema after it was generated from the SDL file ("schema.graphqls"). Specifically,
        // add the "@gp_uppercase" custom directive.
        graphQLSchema = SchemaTransformer.transformSchema(graphQLSchema, new GraphQLTypeVisitorStub() {

            @Override
            public TraversalControl visitGraphQLDirective(GraphQLDirective node, TraverserContext<GraphQLSchemaElement> context) {
                String nodeName = node.getName();
                log.debug("Visiting directive '{}'", nodeName);
                if ("gp_uppercase".equals(nodeName)) {
                    return changeNode(context, uppercaseDirective());
                }
                return super.visitGraphQLDirective(node, context);
            }
        });

        // Create a custom execution strategy that incorporates some custom directives like "@gp_uppercase"
        ExecutionStrategy queryExecutionStrategy = new GpDirectivesExecutionStrategy();

        var build = GraphQL.newGraphQL(graphQLSchema)
                .queryExecutionStrategy(queryExecutionStrategy)
                .build();
        var executionResult = build.execute(graphqlQuery);

        GraphqlUtil.printResult(executionResult);
    }

    public static GraphQLDirective uppercaseDirective() {
        return GraphQLDirective.newDirective()
                .name("gp_uppercase")
                .description("WIP Directs the executor to uppercase strings.")
                .validLocations(FIELD)
                .build();
    }
}
