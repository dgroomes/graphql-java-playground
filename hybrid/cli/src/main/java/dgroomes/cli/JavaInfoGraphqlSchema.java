package dgroomes.cli;

import dgroomes.javainfo.JavaComponent;
import graphql.schema.*;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.util.TraversalControl;
import graphql.util.TraverserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Map;

import static graphql.schema.GraphQLEnumType.newEnum;
import static graphql.schema.idl.RuntimeWiring.newRuntimeWiring;

/**
 * Create a GraphQL schema over the capabilities of the 'java-info' module.
 */
public class JavaInfoGraphqlSchema {

    private static final Logger log = LoggerFactory.getLogger(JavaInfoGraphqlSchema.class);

    public static GraphQLSchema schema() {
        var schemaParser = new SchemaParser();
        var typeDefinitionRegistry = schemaParser.parse(new File("schema.graphqls"));

        Map<String, JavaComponent> javaComponentsById = JavaInfoGraphqlAdapter.javaComponentsById();

        var runtimeWiring = newRuntimeWiring()
                .type("Query", builder -> builder.dataFetcher("javaInfo", new JavaInfoFetcher()))
                .type("JavaComponent", builder -> builder.enumValues(javaComponentsById::get))
                .build();

        var schemaGenerator = new SchemaGenerator();
        var graphQLSchema = schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);

        // Transform the schema after it was generated from the SDL file ("schema.graphqls"). This approach is useful
        // when the schema needs to contain dynamic content that can't realistically be authored in the SDL file, which
        // is a static document.
        //
        // Specifically, transform the "JavaComponent" GraphQL enum by adding each JavaPackage entry to it.
        graphQLSchema = SchemaTransformer.transformSchema(graphQLSchema, new GraphQLTypeVisitorStub() {
            @Override
            public TraversalControl visitGraphQLEnumType(GraphQLEnumType node, TraverserContext<GraphQLSchemaElement> context) {
                if (node.getName().equals("JavaComponent")) {
                    var newNode = newEnum(node);
                    for (Map.Entry<String, JavaComponent> entry : javaComponentsById.entrySet()) {
                        String key = entry.getKey();
                        JavaComponent value = entry.getValue();
                        log.debug("Adding new enum value. key={}, value={}", key, value);
                        newNode.value(key, value);
                    }
                    return changeNode(context, newNode.build());
                } else {
                    return super.visitGraphQLEnumType(node, context);
                }
            }
        });

        return graphQLSchema;
    }
}
