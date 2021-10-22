package dgroomes;

import graphql.GraphQL;
import graphql.Scalars;
import graphql.schema.GraphQLEnumType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZoneId;

import static graphql.schema.GraphQLArgument.newArgument;
import static graphql.schema.GraphQLCodeRegistry.newCodeRegistry;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLObjectType.newObject;
import static graphql.schema.GraphQLSchema.newSchema;

public class LocalTimeGraphql {

    private static final Logger log = LoggerFactory.getLogger(LocalTimeGraphql.class);

    public static void main(String[] args) {
        if (args.length != 1) {
            log.error("Expected exactly one argument but found {}", args.length);
            System.exit(1);
        }

        var graphqlQuery = args[0];

        // Build the schema
        var schemaBuilder = newSchema();
        var timeZoneEnumBuilder = GraphQLEnumType.newEnum().name("TimeZone");
        for (String id : ZoneId.getAvailableZoneIds()) {
            timeZoneEnumBuilder.value(normalizeNameForGraphqlEnum(id), id);
        }

        var timeZoneEnum = timeZoneEnumBuilder.build();
        var timeZoneField = newFieldDefinition()
                .name("localTime")
                .type(Scalars.GraphQLString)
                .argument(newArgument()
                        .name("timezone")
                        .type(timeZoneEnum))
                .build();
        var query = newObject()
                .name("Query")
                .field(timeZoneField)
                .build();
        schemaBuilder.query(query);
        schemaBuilder.codeRegistry(newCodeRegistry()
                .dataFetcher(query, timeZoneField, new LocalTimeDataFetcher())
                .build());
        var graphQLSchema = schemaBuilder.build();

        // Execute the query
        var graphQL = GraphQL.newGraphQL(graphQLSchema).build();
        var executionResult = graphQL.execute(graphqlQuery);

        log.info(executionResult.getData().toString());
    }

    /**
     * Normalize a string so that is legal to use as a GraphQL enum name.
     */
    private static String normalizeNameForGraphqlEnum(String original) {
        return original.toUpperCase()
                .replace('/', '_')
                .replaceAll("[^A-Z_]", "");
    }
}
