package dgroomes.graphql;

import dgroomes.util.ClasspathUtil;
import dgroomes.woodlands.WoodlandType;
import graphql.GraphQL;
import graphql.execution.ExecutionStrategy;
import graphql.schema.idl.NaturalEnumValuesProvider;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static graphql.schema.idl.RuntimeWiring.newRuntimeWiring;

/**
 * A toy GraphQL schema backed by static data: woodlands and their animals.
 */
public class GraphqlWiring {

  private static final Logger log = LoggerFactory.getLogger(GraphqlWiring.class);

  /**
   * Build the GraphQL runtime wiring into the final form: an instance of {@link GraphQL}
   */
  public static GraphQL build() {
    var schemaParser = new SchemaParser();
    var typeRegistry = new TypeDefinitionRegistry();

    List<String> schemaResources = List.of("/schema.graphqls", "/extensions.graphqls");
    for (var schemaResource : schemaResources) {
      String schema = ClasspathUtil.classpathResourceToString(schemaResource);
      typeRegistry.merge(schemaParser.parse(schema));
    }

    var runtimeWiring = newRuntimeWiring()
            .type("Query", builder -> builder.dataFetcher("woodland", new WoodlandDataFetcher()))
            .type("SortOrder", builder -> builder.enumValues(new NaturalEnumValuesProvider<>(SortOrder.class)))
            .type("WoodlandType", builder -> builder.enumValues(new NaturalEnumValuesProvider<>(WoodlandType.class)))
            .build();

    var schemaGenerator = new SchemaGenerator();
    var graphQLSchema = schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);

    // Create a custom execution strategy that incorporates some custom directives like "@gp_uppercase"
    ExecutionStrategy queryExecutionStrategy = new GpDirectivesExecutionStrategy();

    return GraphQL.newGraphQL(graphQLSchema)
            .queryExecutionStrategy(queryExecutionStrategy)
            .build();
  }
}
